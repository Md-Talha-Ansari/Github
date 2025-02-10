package com.tf.app.data.paging

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tf.app.data.database.GithubDatabase
import com.tf.app.data.database.entity.user.UserEntity
import com.tf.app.data.database.entity.user.UserListPageInfoEntity
import com.tf.app.data.remote.dto.user.toUserEntity
import com.tf.app.data.remote.service.user.UserRemoteDataSource
import com.tf.app.data.utils.parseLinkHeader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Headers
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val remote: UserRemoteDataSource,
    private val local: GithubDatabase
) : RemoteMediator<Int, UserEntity>() {

    private val userDao = local.userDao
    private val userListPageInfoDao = local.userListPageInfoDao
    private val userDetailDao = local.userDetailDao

    override suspend fun load(loadType: LoadType, state: PagingState<Int, UserEntity>): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    local.withTransaction {
                        userListPageInfoDao.deleteAll()
                        userDao.deleteAll()
                        userDetailDao.deleteAll()
                    }
                    0
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
                LoadType.APPEND -> {
                    val remoteKeys = getPageKeyForLastItem(state)
                    remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                }
            }
            val response = remote.getUsers(currentPage)
            val next = parseLinksForNext(response.headers())
            val prev = if (loadType == LoadType.APPEND) currentPage else 0
            response.body()?.let {
                local.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        userDao.deleteAll()
                        userListPageInfoDao.deleteAll()
                    }
                    val keys = it.map { user ->
                        UserListPageInfoEntity(
                            id = user.id,
                            prevKey = prev,
                            nextKey = next
                        )
                    }
                    userDao.insertAll(it.map { userDto ->  userDto.toUserEntity()})
                    userListPageInfoDao.insert(keys)
                }
            }
            MediatorResult.Success(endOfPaginationReached = next == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    /**
     * Parse the next link in links to find the next link id.
     * @param headers Header values.
     */
    private fun parseLinksForNext(headers: Headers):Int?{
        val links = headers.parseLinkHeader()
        if(links.containsKey("next")){
            val nextLink = links["next"]
            if(nextLink.isNullOrEmpty()){
                return null
            }else if(nextLink.contains("since=")){
                val uri = Uri.parse(nextLink)
                uri.getQueryParameter("since")?.let {
                    return it.toInt()
                }
            }
        }
        return null
    }

    private suspend fun getPageKeyForLastItem(
        state: PagingState<Int, UserEntity>
    ): UserListPageInfoEntity? {
        return withContext(Dispatchers.IO) {
            state.pages.lastOrNull {
                it.data.isNotEmpty()
            }?.data?.lastOrNull()
            ?.let { user ->
                userListPageInfoDao.getPageInfo(id = user.id)
            }
        }
    }

}