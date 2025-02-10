package com.tf.app.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tf.app.data.database.GithubDatabase
import com.tf.app.data.database.dao.user.UserDao
import com.tf.app.data.database.dao.user.UserDetailDao
import com.tf.app.data.database.entity.user.UserEntity
import com.tf.app.data.paging.UserRemoteMediator
import com.tf.app.data.remote.service.user.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit.Builder): UserService {
        return retrofit.baseUrl("https://api.github.com/").build().create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun providesUserDao(database: GithubDatabase): UserDao {
        return database.userDao
    }

    @Singleton
    @Provides
    fun providesUserDetailDao(database: GithubDatabase): UserDetailDao {
        return database.userDetailDao
    }

}