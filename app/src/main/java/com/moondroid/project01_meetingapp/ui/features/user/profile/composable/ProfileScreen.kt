package com.moondroid.project01_meetingapp.ui.features.user.profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.ui.features.group.list.GroupListScreen
import com.moondroid.project01_meetingapp.ui.features.user.profile.ProfileContract
import com.moondroid.project01_meetingapp.ui.features.user.profile.ProfileViewModel
import com.moondroid.project01_meetingapp.ui.theme.Mint01
import com.moondroid.project01_meetingapp.ui.theme.Typography
import com.moondroid.project01_meetingapp.ui.widget.BaseLayout

@Composable
fun ProfileScreen(navigateToGroup: (title: String) -> Unit, navigateUp: () -> Unit) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.event.send(ProfileContract.Event.LoadProfile)
    }

    BaseLayout("프로필", onBack = navigateUp) {
        Column {
            UserCard(uiState.profile)

            GroupListScreen(uiState.groupList) {
                navigateToGroup(it.title)
            }
        }
    }
}

@Composable
fun UserCard(profile: Profile) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .width(120.dp)
                .padding(vertical = 15.dp, horizontal = 10.dp)
                .aspectRatio(1f),
            shape = CircleShape
        ) {
            AsyncImage(profile.thumb, contentDescription = "thumb")
        }

        Spacer(Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 15.dp, end = 10.dp)
        ) {
            Text(profile.name, style = Typography.bodyMedium)
            Spacer(Modifier.height(10.dp))
            Text(profile.message, style = Typography.bodySmall)
        }
    }
}