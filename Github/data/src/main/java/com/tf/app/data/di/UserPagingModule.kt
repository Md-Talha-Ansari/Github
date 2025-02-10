package com.tf.app.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tf.app.data.database.dao.user.UserDao
import com.tf.app.data.database.entity.user.UserEntity
import com.tf.app.data.database.entity.user.toUser
import com.tf.app.data.model.user.User
import com.tf.app.data.paging.UserRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Module
@InstallIn(ViewModelComponent::class)
class UserPagingModule {

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    fun providesUserPager(userDao: UserDao, userRemoteMediator: UserRemoteMediator): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 100),
            remoteMediator = userRemoteMediator,
            pagingSourceFactory = { userDao.getUsers() }
        ).flow
            .map {
                    userPagingData ->
                userPagingData.map { it.toUser() }
            }
    }



}