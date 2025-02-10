package com.tf.app.github.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tf.app.github.R

@Composable
fun ErrorMessage(uiStates: LiveData<UIStates?>, secondsToReset: LiveData<Long>,onRetry:() -> Unit) {
    val error = uiStates.observeAsState()
    val timeToReset = secondsToReset.observeAsState()
    val (message:String?,retry:Boolean) = when (error.value) {
        UIStates.RATE_LIMIT_EXCEEDED -> {
            val minutes = timeToReset.value?.div(60)
            val seconds = timeToReset.value?.rem(60)
            Pair(String.format(stringResource(id = R.string.rate_limit_exceeded),minutes,seconds),false)
        }
        UIStates.NETWORK_ERROR -> {
            Pair(stringResource(id = R.string.network_error),true)
        }
        UIStates.UNKNOWN_ERROR -> {
            Pair(stringResource(id = R.string.unknown_error),true)
        }
        UIStates.RETRY -> {
            Pair(null,false)
        }
        else -> {
            Pair(null,false)
        }
    }

    if(message != null) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().background(MaterialTheme.colorScheme.errorContainer).padding(10.dp)) {
            Text(text = message, fontSize = 16.sp,color = MaterialTheme.colorScheme.onErrorContainer)
            if(retry) {
                Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(10.dp),horizontalAlignment = Alignment.End) {
                    Button(modifier = Modifier.height(48.dp).width(100.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onErrorContainer),onClick = { onRetry() }) {
                        Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)  {
                            Text(text = "RETRY", fontSize = 14.sp ,color = MaterialTheme.colorScheme.onError, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(message:String,actionLabel:String,onClose:() -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().background(MaterialTheme.colorScheme.errorContainer).padding(10.dp)) {
        Text(text = message, fontSize = 16.sp,color = MaterialTheme.colorScheme.onErrorContainer)
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(10.dp),horizontalAlignment = Alignment.End) {
            Button(modifier = Modifier.height(48.dp).width(100.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onErrorContainer),onClick = { onClose() }) {
                Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)  {
                    Text(text = actionLabel, fontSize = 14.sp ,color = MaterialTheme.colorScheme.onError, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview
@Composable
fun ErrorMessagePreview() {
    ErrorMessage(MutableLiveData(UIStates.NETWORK_ERROR),MutableLiveData(120L)) { }
}

@Preview
@Composable
fun CloseMessagePreview() {
    ErrorMessage("Error message to close.","Close") { }
}