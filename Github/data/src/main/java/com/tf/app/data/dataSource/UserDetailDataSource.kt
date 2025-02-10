package com.tf.app.data.dataSource

import androidx.room.withTransaction
import com.tf.app.data.database.GithubDatabase
import com.tf.app.data.database.entity.user.toUserDetail
import com.tf.app.data.model.user.UserDetail
import com.tf.app.data.remote.NotFoundException
import com.tf.app.data.remote.RateLimitExceededException
import com.tf.app.data.remote.RemoteException
import com.tf.app.data.remote.dto.user.toUserDetailEntity
import com.tf.app.data.remote.service.user.UserRemoteDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlin.jvm.Throws

@ViewModelScoped
class UserDetailDataSource @Inject constructor(private val remoteDataSource: UserRemoteDataSource,private val localDataSource: GithubDatabase) {

    @Throws(RateLimitExceededException::class, RemoteException::class, NotFoundException::class)
    suspend fun getUserDetail(login: String): UserDetail {
        val userDetailEntity = localDataSource.userDetailDao.getUserDetail(login)
        if (userDetailEntity == null) {
            val response = remoteDataSource.getUser(login)
            val userDetail = response.body()!!.toUserDetailEntity()
            localDataSource.withTransaction {
                localDataSource.userDetailDao.insert(userDetail)
            }
            return userDetail.toUserDetail()
        } else {
            return userDetailEntity.toUserDetail()
        }
    }




}