package com.tf.app.data.dataSource

import androidx.paging.PagingData
import com.tf.app.data.model.user.User
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class UserListDataSource @Inject constructor(private val pageFlow: Flow<PagingData<User>>) {

    fun getUsersPageFlow(): Flow<PagingData<User>> {
        return pageFlow
    }

}