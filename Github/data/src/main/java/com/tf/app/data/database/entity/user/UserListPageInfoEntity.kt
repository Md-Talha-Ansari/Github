package com.tf.app.data.database.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user_list_page_info")
data class UserListPageInfoEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo("prev_key")
    val prevKey: Int?,
    @ColumnInfo("next_key")
    val nextKey: Int?
)
