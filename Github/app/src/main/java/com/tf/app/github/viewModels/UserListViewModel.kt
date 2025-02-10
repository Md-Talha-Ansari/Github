package com.tf.app.github.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tf.app.data.dataSource.UserListDataSource
import com.tf.app.data.model.user.User
import com.tf.app.data.remote.RateLimitExceededException
import com.tf.app.data.remote.RemoteException
import com.tf.app.github.ui.components.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    userListDataSource: UserListDataSource
) : ViewModel() {

    val userListFlow = userListDataSource.getUsersPageFlow().cachedIn(viewModelScope)

    private val _errorState = MutableLiveData<UIStates?>(null)
    val errorState: LiveData<UIStates?> = _errorState
    private val _timeToReset = MutableLiveData<Long>(null)
    val timeToReset: LiveData<Long> = _timeToReset

    fun onError(throwable: Throwable) {
        Log.d("--->","Refresh Error ${throwable.stackTraceToString()}")
        if (throwable is RateLimitExceededException) {
            _errorState.postValue(UIStates.RATE_LIMIT_EXCEEDED)
            val systemTime = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
            val resetTimeDuration = throwable.timeToReset - TimeUnit.MILLISECONDS.toSeconds(systemTime)
            if(resetTimeDuration > 0) {
                _timeToReset.postValue(resetTimeDuration)
                startTimerForUpdating(time = resetTimeDuration)
            } else {
                _timeToReset.postValue(0)
            }
        } else if ( throwable is RemoteException) {
            _errorState.postValue(UIStates.NETWORK_ERROR)
        } else {
            _errorState.postValue(UIStates.UNKNOWN_ERROR)
        }
    }

    fun onRetry() {
        _timeToReset.postValue(0)
        _errorState.postValue(UIStates.RETRY)
    }

    private fun startTimerForUpdating(time: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            var timeCount = time
            while(timeCount > 0) {
                delay(1000)
                timeCount = timeCount.dec()
                _timeToReset.postValue(timeCount)
            }
            onRetry()
        }
    }
}