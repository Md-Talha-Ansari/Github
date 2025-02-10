package com.tf.app.github

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.tf.app.data.model.user.User
import com.tf.app.github.ui.components.UserListScreen
import com.tf.app.github.ui.theme.GithubTheme
import com.tf.app.github.viewModels.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: UserListViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(id = R.string.user_list_title)) },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        )
                    },
                    modifier = Modifier.fillMaxSize())
                { innerPadding ->
                    Column(modifier = Modifier.fillMaxSize().padding(innerPadding), verticalArrangement = Arrangement.SpaceBetween) {
                        UserListScreen(
                            viewModel.userListFlow.collectAsLazyPagingItems(),
                            Modifier,
                            onUserClick = {
                                launchUserDetailActivity(it)
                            },
                            onError = {
                                viewModel.onError(it)
                            },
                            viewModel.errorState,
                            viewModel.timeToReset,
                            onRetry = {
                                viewModel.onRetry()
                            }
                        )
                    }
                }
            }
        }
    }

    private fun launchUserDetailActivity(user: User) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("login",user.login)
        startActivity(intent)
    }

}
