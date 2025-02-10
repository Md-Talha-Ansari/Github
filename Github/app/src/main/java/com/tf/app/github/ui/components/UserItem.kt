package com.tf.app.github.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tf.app.data.model.user.User

@Composable
fun UserItem(user: User, onClick: (User) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().height(100.dp)) {
        Card(modifier = Modifier.fillMaxSize().padding(10.dp).clickable { onClick(user) },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            elevation = CardDefaults.cardElevation(5.dp)) {
            Row(modifier = Modifier.fillMaxSize().padding(10.dp), verticalAlignment = Alignment.CenterVertically,) {
                AsyncImage(model = user.avatarUrl,
                    contentDescription = "User Avatar.",
                    modifier = Modifier.height(60.dp).width(60.dp).clip(CircleShape))
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(text = user.login, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                    if(!user.name.isNullOrEmpty()) {
                        Text(text = user.name!!, style = MaterialTheme.typography.titleSmall, maxLines = 1)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun UserItemPreview() {
    val dummyUser = User(
        login = "richcollins",
        id = 135,
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
        name = "name"
    )
    UserItem(dummyUser) { }
}
