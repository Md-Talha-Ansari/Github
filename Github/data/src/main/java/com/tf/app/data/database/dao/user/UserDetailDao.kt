package com.tf.app.data.database.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tf.app.data.database.entity.user.UserDetailEntity

@Dao
interface UserDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDetail: UserDetailEntity)

    @Query("SELECT * FROM user_detail WHERE login = :login")
    fun getUserDetail(login: String): UserDetailEntity?

    @Query("DELETE FROM user_detail")
    fun deleteAll()

}