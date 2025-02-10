package com.tf.app.data.database.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tf.app.data.database.entity.user.UserListPageInfoEntity

@Dao
interface UserListPageInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDetail: List<UserListPageInfoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDetail: UserListPageInfoEntity)

    @Query("SELECT * FROM user_list_page_info WHERE id = :id")
    fun getPageInfo(id: Int): UserListPageInfoEntity

    @Query("DELETE FROM user_list_page_info")
    fun deleteAll()
}