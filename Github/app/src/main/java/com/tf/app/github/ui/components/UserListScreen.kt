package com.tf.app.github.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.tf.app.data.model.user.User

@Composable
fun UserListScreen(
    users: LazyPagingItems<User>,
    modifier: Modifier = Modifier,
    onUserClick: (User) -> Unit,
    onError: (Throwable) -> Unit,
    uiStates: LiveData<UIStates?>,
    timeToReset: LiveData<Long>,
    onRetry:() -> Unit
) {

    val uiState = uiStates.observeAsState()
    if(uiState.value == UIStates.RETRY) {
        users.retry()
    }

    LaunchedEffect(key1 = users.loadState) {
        if(users.loadState.refresh is LoadState.Error) {
            val err = users.loadState.refresh as LoadState.Error
            onError(err.error)
        } else if (users.loadState.append is LoadState.Error) {
            val err = users.loadState.append as LoadState.Error
            onError(err.error)
        }
    }

    Column {
        LazyColumn(modifier = modifier.fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            if (users.loadState.refresh is LoadState.Loading) {
                item {
                    LoadingSpinner()
                }
            } else if (users.loadState.refresh is LoadState.NotLoading) {
                items(users.itemCount) { index ->
                    val user = users[index]
                    if (user != null) {
                        UserItem(user = user, onClick = onUserClick)
                    }
                }
            } else if (users.loadState.append is LoadState.Loading) {
                item {
                    LoadingSpinner()
                }
            }

            if(uiState.value != null && uiState.value != UIStates.RETRY && uiState.value != UIStates.LOADING) {
                item {
                    ErrorMessage(uiStates,timeToReset,onRetry)
                }
            }
        }
    }

}

