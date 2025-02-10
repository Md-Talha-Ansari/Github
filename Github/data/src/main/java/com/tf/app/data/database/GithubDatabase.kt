package com.tf.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tf.app.data.database.dao.user.UserDao
import com.tf.app.data.database.dao.user.UserDetailDao
import com.tf.app.data.database.dao.user.UserListPageInfoDao
import com.tf.app.data.database.entity.user.UserDetailEntity
import com.tf.app.data.database.entity.user.UserEntity
import com.tf.app.data.database.entity.user.UserListPageInfoEntity

@Database(entities = [UserEntity::class, UserDetailEntity::class, UserListPageInfoEntity::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val userDetailDao: UserDetailDao
    abstract val userListPageInfoDao: UserListPageInfoDao
}