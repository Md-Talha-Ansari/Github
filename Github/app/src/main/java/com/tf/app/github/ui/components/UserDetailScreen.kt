package com.tf.app.github.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil3.compose.AsyncImage
import com.tf.app.data.model.user.UserDetail
import com.tf.app.github.R

@Composable
fun UserDetailScreen(modifier: Modifier,userDetail: LiveData<UserDetail>,errorState: LiveData<UIStates?>,timeToReset: LiveData<Long>, onRetry: () -> Unit, onClose: () -> Unit) {
    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface).verticalScroll(
        rememberScrollState()
    )) {
        val user by userDetail.observeAsState()
        val error by errorState.observeAsState()
        if (error != null) {
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                when (error) {
                    UIStates.LOADING -> {
                        LoadingSpinner()
                    }
                    UIStates.CLOSE -> {
                        ErrorMessage(
                            stringResource(R.string.user_not_found),
                            stringResource(R.string.close),
                            onClose
                        )
                    }
                    else -> {
                        ErrorMessage(errorState, timeToReset, onRetry)
                    }
                }
            }
        } else if (user == null) {
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                ErrorMessage(
                    stringResource(R.string.user_not_found),
                    stringResource(R.string.close),
                    onClose
                )
            }
        } else  {
            Box(
                modifier = Modifier.fillMaxWidth().height(300.dp)
            ) {
                Spacer(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)
                        .background(MaterialTheme.colorScheme.primaryContainer).align(Alignment.TopStart)
                )

                IconButton(onClick = onClose,  modifier = Modifier.size(48.dp).align(Alignment.TopStart)) {
                    Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Location",modifier = Modifier.size(24.dp))
                }

                AsyncImage(
                    modifier = Modifier.size(200.dp)
                        .align(Alignment.Center).clip(CircleShape),
                    model = user!!.avatarUrl,
                    contentDescription = ""
                )
            }

            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "@${user!!.login}", style = MaterialTheme.typography.titleSmall)
                if(!user!!.name.isNullOrEmpty()) {
                    Text(text = user!!.name!!, style = MaterialTheme.typography.titleSmall)
                }
            }

            if (!user!!.company.isNullOrEmpty()) {
                Row (modifier = Modifier.fillMaxWidth().height(48.dp),horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(R.drawable.company_svgrepo_com), contentDescription = "Location",modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = user!!.company!!, style = MaterialTheme.typography.bodySmall)
                }
            }

            if (!user!!.location.isNullOrEmpty()) {
                Row (modifier = Modifier.fillMaxWidth().height(48.dp),horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Place, contentDescription = "Location",modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = user!!.location!!, style = MaterialTheme.typography.bodySmall)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(1f).height(80.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = user!!.followers.toString(), style = MaterialTheme.typography.bodySmall)
                    Text(text = stringResource(R.string.followers), style = MaterialTheme.typography.titleSmall)
                }

                Spacer(
                    modifier = Modifier.width(10.dp).fillMaxHeight()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = user!!.following.toString(), style = MaterialTheme.typography.bodySmall)
                    Text(text = stringResource(R.string.following), style = MaterialTheme.typography.titleSmall)
                }

                Spacer(
                    modifier = Modifier.width(10.dp).fillMaxHeight()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = user!!.publicRepos.toString(), style = MaterialTheme.typography.bodySmall)
                    Text(text = stringResource(R.string.public_repos), style = MaterialTheme.typography.titleSmall)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(1f).height(80.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = user!!.publicGists.toString(), style = MaterialTheme.typography.bodySmall)
                    Text(text = stringResource(R.string.public_gists), style = MaterialTheme.typography.titleSmall)
                }
            }

            if (!user!!.bio.isNullOrEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                ) {
                    Text(
                        text = stringResource(R.string.bio),
                        modifier = Modifier.wrapContentSize().padding(10.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = user!!.bio!!,
                        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(10.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Social media information
            if (!user!!.blog.isNullOrEmpty()) {
                Text(text = buildAnnotatedStringForLink(user!!.blog!!, stringResource(R.string.blog), MaterialTheme.colorScheme.primary), style = MaterialTheme.typography.bodyMedium)
            }

            if (!user!!.twitterUsername.isNullOrEmpty()) {
                Text(text = buildAnnotatedStringForLink(String.format(stringResource(R.string.twitter_url),user!!.twitterUsername), stringResource(R.string.twitter), Color.Blue), style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}

@Preview
@Composable
fun UserDetailScreenPreview() {
    val dummyUser = UserDetail(
        login = "abhay",
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

    UserDetailScreen(Modifier,MutableLiveData(dummyUser),MutableLiveData(null),MutableLiveData(120L),{},{})
}

@Preview
@Composable
fun UserDetailScreenLoadingPreview() {
    UserDetailScreen(Modifier,MutableLiveData(null),MutableLiveData(UIStates.LOADING),MutableLiveData(120L),{},{})
}