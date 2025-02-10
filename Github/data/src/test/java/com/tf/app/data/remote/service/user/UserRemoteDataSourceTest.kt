package com.tf.app.data.remote.service.user

import com.tf.app.data.remote.NotFoundException
import com.tf.app.data.remote.RateLimitExceededException
import com.tf.app.data.remote.service.user.mock.UserServiceResponseMock
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserRemoteDataSourceTest {

    @Mock
    lateinit var userService: UserService
    lateinit var userRemoteDataSource: UserRemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userRemoteDataSource = UserRemoteDataSource(userService)
    }

    @Test
    fun testGetUsersSuccess() = runTest {
        val dummyUsers = UserServiceResponseMock.getUsersSuccessResponse(since = 0)
        Mockito.`when`(userService.getUsers(0)).thenReturn(dummyUsers)
        val response = userRemoteDataSource.getUsers(0)
        assert(response.isSuccessful)
        assert(response.body() == dummyUsers.body())
        assert(!response.body().isNullOrEmpty())
    }

    @Test (expected= RateLimitExceededException::class)
    fun testGetUsersFail() = runTest {
        val dummyUsers = UserServiceResponseMock.getUsersFailResponse(since = 0)
        Mockito.`when`(userService.getUsers(0)).thenReturn(dummyUsers)
        userRemoteDataSource.getUsers(0)
    }

    @Test
    fun testGetUser() = runTest {
        val dummyUser = UserServiceResponseMock.getUserSuccessResponse("login")
        Mockito.`when`(userService.getUser("login")).thenReturn(dummyUser)
        val response = userRemoteDataSource.getUser("login")
        assert(response.isSuccessful)
        assert(response.body() != null)
        response.body()?.let {
            assert(it == dummyUser.body())
        }
    }

    @Test (expected= RateLimitExceededException::class)
    fun testGetUserFail() = runTest {
        val dummyUser = UserServiceResponseMock.getUserDetailFailResponse()
        Mockito.`when`(userService.getUser("login")).thenReturn(dummyUser)
        userRemoteDataSource.getUser("login")
    }

    @Test (expected= NotFoundException::class)
    fun testUserNotFound() = runTest {
        val dummyUser = UserServiceResponseMock.getUserDetailNotFoundResponse()
        Mockito.`when`(userService.getUser("login")).thenReturn(dummyUser)
        userRemoteDataSource.getUser("login")
    }

}