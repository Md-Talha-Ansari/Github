package com.tf.app.data.remote.dto.user

import com.google.gson.annotations.SerializedName
import com.tf.app.data.database.entity.user.UserDetailEntity

data class UserDetailDto(
    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("bio")
    val bio: String?, // Can be null

    @SerializedName("blog")
    val blog: String?,

    @SerializedName("company")
    val company: String?,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("email")
    val email: String?, // Can be null

    @SerializedName("events_url")
    val eventsUrl: String,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("followers_url")
    val followersUrl: String,

    @SerializedName("following")
    val following: Int,

    @SerializedName("following_url")
    val followingUrl: String,

    @SerializedName("gists_url")
    val gistsUrl: String,

    @SerializedName("gravatar_id")
    val gravatarId: String?, // Can be null

    @SerializedName("hireable")
    val hireable: String?, // Can be null

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("location")
    val location: String?,

    @SerializedName("login")
    val login: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("node_id")
    val nodeId: String,

    @SerializedName("notification_email")
    val notificationEmail: String?,

    @SerializedName("organizations_url")
    val organizationsUrl: String,

    @SerializedName("public_gists")
    val publicGists: Int,

    @SerializedName("public_repos")
    val publicRepos: Int,

    @SerializedName("received_events_url")
    val receivedEventsUrl: String,

    @SerializedName("repos_url")
    val reposUrl: String,

    @SerializedName("site_admin")
    val siteAdmin: Boolean,

    @SerializedName("starred_url")
    val starredUrl: String,

    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String,

    @SerializedName("twitter_username")
    val twitterUsername: String?,

    @SerializedName("type")
    val type: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("user_view_type")
    val userViewType: String?
)

fun UserDetailDto.toUserDetailEntity() = UserDetailEntity(
    id = id,
    avatarUrl = avatarUrl,
    bio = bio,
    blog = blog,
    company = company,
    createdAt = createdAt,
    email = email,
    eventsUrl = eventsUrl,
    followers = followers,
    followersUrl = followingUrl,
    following = following,
    followingUrl = followingUrl,
    gistsUrl = gistsUrl,
    gravatarId = gravatarId,
    hireable = hireable,
    htmlUrl = htmlUrl,
    location = location,
    login = login,
    name = name,
    nodeId = nodeId,
    notificationEmail = notificationEmail,
    organizationsUrl = organizationsUrl,
    publicGists = publicGists,
    publicRepos = publicRepos,
    receivedEventsUrl = receivedEventsUrl,
    reposUrl = reposUrl,
    siteAdmin = siteAdmin,
    starredUrl = starredUrl,
    subscriptionsUrl = subscriptionsUrl,
    twitterUsername = twitterUsername,
    type = type,
    updatedAt = updatedAt,
    url = url,
    userViewType = userViewType
)