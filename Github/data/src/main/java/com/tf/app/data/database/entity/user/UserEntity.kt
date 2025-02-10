package com.tf.app.data.database.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tf.app.data.model.user.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo("name")
    val name: String?,
    @ColumnInfo("email")
    val email: String?,
    @ColumnInfo("login")
    val login: String,
    @ColumnInfo("node_id")
    val nodeId: String,
    @ColumnInfo("avatar_url")
    val avatarUrl: String,
    @ColumnInfo("gravatar_id")
    val gravatarId: String?, // Can be null
    @ColumnInfo("url")
    val url: String,
    @ColumnInfo("html_url")
    val htmlUrl: String,
    @ColumnInfo("followers_url")
    val followersUrl: String,
    @ColumnInfo("following_url")
    val followingUrl: String,
    @ColumnInfo("gists_url")
    val gistsUrl: String,
    @ColumnInfo("starred_url")
    val starredUrl: String,
    @ColumnInfo("subscriptions_url")
    val subscriptionsUrl: String,
    @ColumnInfo("organizations_url")
    val organizationsUrl: String,
    @ColumnInfo("repos_url")
    val reposUrl: String,
    @ColumnInfo("events_url")
    val eventsUrl: String,
    @ColumnInfo("received_events_url")
    val receivedEventsUrl: String,
    @ColumnInfo("type")
    val type: String,
    @ColumnInfo("site_admin")
    val siteAdmin: Boolean,
    @ColumnInfo("starred_at")
    val starredAt: String?,
    @ColumnInfo("user_view_type")
    val userViewType: String?
)

fun UserEntity.toUser() =  User(
    id = id,
    name = name,
    email = email,
    login = login,
    nodeId = nodeId,
    avatarUrl = avatarUrl,
    gravatarId = gravatarId,
    url = url,
    htmlUrl = htmlUrl,
    followersUrl = followersUrl,
    followingUrl = followingUrl,
    gistsUrl = gistsUrl,
    starredUrl = starredUrl,
    subscriptionsUrl = subscriptionsUrl,
    organizationsUrl = organizationsUrl,
    reposUrl = reposUrl,
    eventsUrl = eventsUrl,
    receivedEventsUrl = receivedEventsUrl,
    type = type,
    siteAdmin = siteAdmin,
    starredAt = starredAt,
    userViewType = userViewType
)