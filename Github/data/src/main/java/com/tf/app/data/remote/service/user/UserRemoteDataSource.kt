package com.tf.app.data.remote.service.user

import com.tf.app.data.remote.NotFoundException
import com.tf.app.data.remote.RateLimitExceededException
import com.tf.app.data.remote.RemoteException
import com.tf.app.data.remote.dto.user.UserDetailDto
import com.tf.app.data.remote.dto.user.UserDto
import com.tf.app.data.utils.getLimit
import com.tf.app.data.utils.getLimitRemaining
import com.tf.app.data.utils.getLimitReset
import com.tf.app.data.utils.getLimitUsed
import okhttp3.Headers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(private val userService: UserService) {

    private var _limit: Int = Int.MAX_VALUE
    private var _limitRemaining: Int = 0
    private var _limitUsed: Int = 0
    private var _timeToResetLimit: Int = 0


    @Throws(RateLimitExceededException::class, RemoteException::class, NotFoundException::class)
    suspend fun getUsers(since: Int): Response<List<UserDto>> {
        val response = userService.getUsers(since)
        updateLimitInfo(response.headers())
        if (response.isSuccessful) {
            return response
        } else {
            throw parseErrorCode(response)
        }
    }

    @Throws(RateLimitExceededException::class, RemoteException::class, NotFoundException::class)
    suspend fun getUser(userLogin: String): Response<UserDetailDto> {
        val response = userService.getUser(userLogin)
        updateLimitInfo(response.headers())
        if (response.isSuccessful) {
            return response
        } else {
            throw parseErrorCode(response)
        }
    }

    fun <T> parseErrorCode(response: Response<T>): Exception {
        return if (response.code() == 403) {
            RateLimitExceededException(_limit, _limitRemaining, _limitUsed, _timeToResetLimit)
        } else if (response.code() == 404) {
            NotFoundException(response.errorBody().toString())
        } else {
            RemoteException(response.errorBody().toString())
        }
    }

    private fun updateLimitInfo(headers: Headers) {
        _limit = headers.getLimit()
        _limitRemaining = headers.getLimitRemaining()
        _limitUsed = headers.getLimitUsed()
        _timeToResetLimit = headers.getLimitReset()
    }
}