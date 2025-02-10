package com.tf.app.data.remote.service.user.mock

import com.tf.app.data.remote.dto.user.UserDetailDto
import com.tf.app.data.remote.dto.user.UserDto
import okhttp3.Headers
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object UserServiceResponseMock {

    suspend fun getUsersSuccessResponse(since: Int): Response<List<UserDto>> {
        return Response.success(dummyUsers(),dummyUserListSuccessResponseHeader(since))
    }

    suspend fun getUserSuccessResponse(userLogin: String): Response<UserDetailDto> {
        return Response.success(dummyUserDto(userLogin),dummyUserDetailSuccessResponseHeader())
    }

    suspend fun getUsersFailResponse(since: Int): Response<List<UserDto>> {
        val headers = dummyUserListFailResponseHeader(since)
        val response = Response
            .error<List<UserDto>?>(403, dummyRateLimitErrorResponse().toResponseBody())
            .raw().newBuilder()
            .headers(headers).build()
        return Response.error(dummyRateLimitErrorResponse().toResponseBody(),response)
    }

    suspend fun getUserDetailFailResponse(): Response<UserDetailDto> {
        val headers = dummyUserDetailFailResponseHeader()
        val response = Response
            .error<List<UserDto>?>(403, dummyRateLimitErrorResponse().toResponseBody())
            .raw().newBuilder()
            .headers(headers).build()
        return Response.error(dummyRateLimitErrorResponse().toResponseBody(),response)
    }

    suspend fun getUserDetailNotFoundResponse(): Response<UserDetailDto> {
        val headers = dummyUserDetailSuccessResponseHeader()
        val response = Response
            .error<List<UserDto>?>(404, dummyNotFoundErrorResponse().toResponseBody())
            .raw().newBuilder()
            .headers(headers).build()
        return Response.error(dummyRateLimitErrorResponse().toResponseBody(),response)
    }

    private fun dummyUserDto(login:String): UserDetailDto {
        return UserDetailDto(
            login = login,
            id = 75,
            nodeId = "MDQ6VXNlcjc1",
            avatarUrl = "https://avatars.githubusercontent.com/u/75?v=4",
            gravatarId = "",
            url = "https://api.github.com/users/abhay",
            htmlUrl = "https://github.com/abhay",
            followersUrl = "https://api.github.com/users/abhay/followers",
            followingUrl = "https://api.github.com/users/abhay/following{/other_user}",
            gistsUrl = "https://api.github.com/users/abhay/gists{/gist_id}",
            starredUrl = "https://api.github.com/users/abhay/starred{/owner}{/repo}",
            subscriptionsUrl = "https://api.github.com/users/abhay/subscriptions",
            organizationsUrl = "https://api.github.com/users/abhay/orgs",
            reposUrl = "https://api.github.com/users/abhay/repos",
            eventsUrl = "https://api.github.com/users/abhay/events{/privacy}",
            receivedEventsUrl = "https://api.github.com/users/abhay/received_events",
            type = "User",
            userViewType = "public",
            siteAdmin = false,
            name = "Abhay Kumar",
            company = "@helium",
            blog = "https://hackshare.com",
            location = "San Francisco, CA",
            email = null,
            hireable = null,
            bio = "nostr: npub170uwqlxtwefytnpvek42qsazs0wq0ej856eewectq3dzydruvxkqqtgzs7",
            twitterUsername =  "abhay",
            publicRepos = 42,
            publicGists = 2,
            followers = 184,
            following = 1,
            createdAt = "2008-01-28T21:08:23Z",
            updatedAt = "2025-01-20T15:57:13Z",
            notificationEmail = null
        )
    }

    private fun dummyUsers(): List<UserDto> {
        val list = MutableList(100) {
            UserDto(
                login = "richcollins$it",
                id = it,
                nodeId = "MDQ6VXNlcjEzNQ==",
                avatarUrl = "https://avatars.githubusercontent.com/u/135?v=4",
                gravatarId = "",
                url = "https://api.github.com/users/richcollins",
                htmlUrl = "https://github.com/richcollins",
                followersUrl = "https://api.github.com/users/richcollins/followers",
                followingUrl = "https://api.github.com/users/richcollins/following{/other_user}",
                gistsUrl = "https://api.github.com/users/richcollins/gists{/gist_id}",
                starredUrl = "https://api.github.com/users/richcollins/starred{/owner}{/repo}",
                subscriptionsUrl = "https://api.github.com/users/richcollins/subscriptions",
                organizationsUrl = "https://api.github.com/users/richcollins/orgs",
                reposUrl = "https://api.github.com/users/richcollins/repos",
                eventsUrl = "https://api.github.com/users/richcollins/events{/privacy}",
                receivedEventsUrl = "https://api.github.com/users/richcollins/received_events",
                type = "User",
                userViewType = "public",
                siteAdmin = false,
                email = "",
                starredAt = null,
                name = "name$it"
            )
        }
        return list
    }


    private fun dummyUserDetailSuccessResponseHeader(): Headers {
        return Headers.Builder()
            .add("Content-Type","application/json; charset=utf-8")
            .add("Cache-Control","public, max-age=60, s-maxage=60")
            .add("X-GitHub-Media-Type","github.v3; format=json")
            .add("Server","github.com")
            .add("X-RateLimit-Limit","60")
            .add("X-RateLimit-Remaining","59")
            .add("X-RateLimit-Reset","1739173001")
            .add("X-RateLimit-Used","1")
            .build()
    }


    private fun dummyUserDetailFailResponseHeader(): Headers {
        return Headers.Builder()
            .add("Content-Type","application/json; charset=utf-8")
            .add("Cache-Control","public, max-age=60, s-maxage=60")
            .add("X-GitHub-Media-Type","github.v3; format=json")
            .add("Server","github.com")
            .add("X-RateLimit-Limit","60")
            .add("X-RateLimit-Remaining","0")
            .add("X-RateLimit-Reset","1739173001")
            .add("X-RateLimit-Used","60")
            .build()
    }

    private fun dummyUserListSuccessResponseHeader(since: Int): Headers {
        val link = "<https://api.github.com/users?per_page=100&since=$since>; rel=\"next\", <https://api.github.com/users{?$since}>; rel=\"first\""
        return Headers.Builder()
            .add("Content-Type","application/json; charset=utf-8")
            .add("Cache-Control","public, max-age=60, s-maxage=60")
            .add("X-GitHub-Media-Type","github.v3; format=json")
            .add("Server","github.com")
            .add("X-RateLimit-Limit","60")
            .add("X-RateLimit-Remaining","40")
            .add("X-RateLimit-Reset","1739173001")
            .add("X-RateLimit-Used","20")
            .add("Link",link)
            .build()
    }

    private fun dummyUserListFailResponseHeader(since: Int): Headers {
        val link = "<https://api.github.com/users?per_page=100&since=$since>; rel=\"next\", <https://api.github.com/users{?$since}>; rel=\"first\""
        return Headers.Builder()
            .add("Content-Type","application/json; charset=utf-8")
            .add("Cache-Control","public, max-age=60, s-maxage=60")
            .add("X-GitHub-Media-Type","github.v3; format=json")
            .add("Server","github.com")
            .add("X-RateLimit-Limit","60")
            .add("X-RateLimit-Remaining","0")
            .add("X-RateLimit-Reset","1739173001")
            .add("X-RateLimit-Used","60")
            .add("Link",link)
            .build()
    }

    private fun dummyRateLimitErrorResponse() : String {
        return "{\n" +
                "    \"message\": \"API rate limit exceeded for 133.106.49.56. (But here's the good news: Authenticated requests get a higher rate limit. Check out the documentation for more details.)\",\n" +
                "    \"documentation_url\": \"https://docs.github.com/rest/overview/resources-in-the-rest-api#rate-limiting\"\n" +
                "}"
    }

    private fun dummyNotFoundErrorResponse() : String {
        return "{\n" +
                "    \"message\": \"Not Found\",\n" +
                "    \"documentation_url\": \"https://docs.github.com/rest\",\n" +
                "    \"status\": \"404\"\n" +
                "}"
    }

}