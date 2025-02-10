package com.tf.app.data.remote.service.user

import com.tf.app.data.remote.dto.user.UserDetailDto
import com.tf.app.data.remote.dto.user.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("/users")
    suspend fun getUsers(@Query("since") since: Int, @Query("per_page") perPage: Int = MAX_PER_PAGE): Response<List<UserDto>>

    @GET("/users/{userLogin}")
    suspend fun getUser(@Path("userLogin") userLogin: String): Response<UserDetailDto>

    companion object {
        private const val MAX_PER_PAGE = 100
    }

}