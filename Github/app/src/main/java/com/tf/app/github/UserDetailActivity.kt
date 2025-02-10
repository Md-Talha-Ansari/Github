package com.tf.app.github

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.tf.app.github.ui.components.UserDetailScreen
import com.tf.app.github.ui.theme.GithubTheme
import com.tf.app.github.viewModels.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : ComponentActivity() {
    private val viewModel : UserDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userLogin = intent.getStringExtra("login")
        if(userLogin == null) {
            finish()
        } else {
            viewModel.fetchUserDetail(userLogin)
        }
        setContent {
            GithubTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserDetailScreen(modifier = Modifier.padding(innerPadding),viewModel.userDetail,
                        viewModel.errorState,viewModel.timeToReset, onRetry = {
                            viewModel.fetchUserDetail(userLogin!!)
                        }, onClose = {
                            finish()
                        })
                }
            }
        }
    }
}
