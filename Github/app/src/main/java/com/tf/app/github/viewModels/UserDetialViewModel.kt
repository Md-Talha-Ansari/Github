package com.tf.app.github.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tf.app.data.dataSource.UserDetailDataSource
import com.tf.app.data.model.user.UserDetail
import com.tf.app.data.remote.NotFoundException
import com.tf.app.data.remote.RateLimitExceededException
import com.tf.app.data.remote.RemoteException
import com.tf.app.github.ui.components.UIStates
import com.tf.app.github.utils.DispatchersUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(private val userDataSource: UserDetailDataSource,private val dispatchersUtil: DispatchersUtil): ViewModel() {

    private val _errorState = MutableLiveData<UIStates?>(null)
    val errorState: LiveData<UIStates?> = _errorState

    private val _timeToReset = MutableLiveData<Long>(null)
    val timeToReset: LiveData<Long> = _timeToReset

    private var _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail
    private lateinit var userLogin:String

    fun fetchUserDetail(userLogin: String) {
        if(!::userLogin.isInitialized) {
            this.userLogin = userLogin
        }
        viewModelScope.launch(dispatchersUtil.io()) {
            _errorState.postValue(UIStates.LOADING)
            try {
                val userDetail = userDataSource.getUserDetail(userLogin)
                _errorState.postValue(null)
                _userDetail.postValue(userDetail)
            } catch (e: RateLimitExceededException) {
                _errorState.postValue(UIStates.RATE_LIMIT_EXCEEDED)
                val systemTime = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
                val resetTimeDuration = e.timeToReset - TimeUnit.MILLISECONDS.toSeconds(systemTime)
                if(resetTimeDuration > 0) {
                    _timeToReset.postValue(resetTimeDuration)
                    startTimerForUpdating(time = resetTimeDuration)
                } else {
                    _timeToReset.postValue(0)
                }
            } catch (e: RemoteException) {
                _errorState.postValue(UIStates.NETWORK_ERROR)
            } catch (e: NotFoundException) {
                _errorState.postValue(UIStates.CLOSE)
            } catch (e: Exception) {
                _errorState.postValue(UIStates.UNKNOWN_ERROR)
            }
        }
    }

    private fun startTimerForUpdating(time: Long) {
        viewModelScope.launch(dispatchersUtil.default()) {
            var timeCount = time
            while(timeCount > 0) {
                delay(1000)
                timeCount = timeCount.dec()
                _timeToReset.postValue(timeCount)
            }
            fetchUserDetail(userLogin)
        }
    }

}