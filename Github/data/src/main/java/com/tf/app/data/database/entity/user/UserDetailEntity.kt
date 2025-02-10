package com.tf.app.data.database.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tf.app.data.model.user.UserDetail
import com.tf.app.data.remote.dto.user.UserDetailDto

@Entity(tableName = "user_detail")
data class UserDetailEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo("avatar_url")
    val avatarUrl: String,
    @ColumnInfo("bio")
    val bio: String?,
    @ColumnInfo("blog")
    val blog: String?,
    @ColumnInfo("company")
    val company: String?,
    @ColumnInfo("created_at")
    val createdAt: String,
    @ColumnInfo("email")
    val email: String?, // Can be null
    @ColumnInfo("events_url")
    val eventsUrl: String,
    @ColumnInfo("followers")
    val followers: Int,
    @ColumnInfo("followers_url")
    val followersUrl: String,
    @ColumnInfo("following")
    val following: Int,
    @ColumnInfo("following_url")
    val followingUrl: String,
    @ColumnInfo("gists_url")
    val gistsUrl: String,
    @ColumnInfo("gravatar_id")
    val gravatarId: String?, // Can be null
    @ColumnInfo("hireable")
    val hireable: String?, // Can be null
    @ColumnInfo("html_url")
    val htmlUrl: String,
    @ColumnInfo("location")
    val location: String?,
    @ColumnInfo("login")
    val login: String,
    @ColumnInfo("name")
    val name: String?,
    @ColumnInfo("node_id")
    val nodeId: String,
    @ColumnInfo("notification_email")
    val notificationEmail: String?,
    @ColumnInfo("organizations_url")
    val organizationsUrl: String,
    @ColumnInfo("public_gists")
    val publicGists: Int,
    @ColumnInfo("public_repos")
    val publicRepos: Int,
    @ColumnInfo("received_events_url")
    val receivedEventsUrl: String,
    @ColumnInfo("repos_url")
    val reposUrl: String,
    @ColumnInfo("site_admin")
    val siteAdmin: Boolean,
    @ColumnInfo("starred_url")
    val starredUrl: String,
    @ColumnInfo("subscriptions_url")
    val subscriptionsUrl: String,
    @ColumnInfo("twitter_username")
    val twitterUsername: String?,
    @ColumnInfo("type")
    val type: String,
    @ColumnInfo("updated_at")
    val updatedAt: String,
    @ColumnInfo("url")
    val url: String,
    @ColumnInfo("user_view_type")
    val userViewType: String?
)

fun UserDetailEntity.toUserDetail() = UserDetail(
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