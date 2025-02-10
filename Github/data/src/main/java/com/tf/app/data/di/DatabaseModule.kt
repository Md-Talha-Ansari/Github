package com.tf.app.data.di

import android.content.Context
import androidx.room.Room
import com.tf.app.data.database.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideGithubDatabase(@ApplicationContext context: Context): GithubDatabase {
        return Room.databaseBuilder(context, GithubDatabase::class.java, "github_database").build()
    }

}