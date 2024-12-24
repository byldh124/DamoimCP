package com.moondroid.project01_meetingapp.ui.features.group.composable.pager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.ui.features.group.GroupContract
import com.moondroid.project01_meetingapp.ui.features.group.GroupViewModel
import com.moondroid.project01_meetingapp.ui.theme.Container
import com.moondroid.project01_meetingapp.ui.theme.Red02
import com.moondroid.project01_meetingapp.ui.theme.Typography
import com.moondroid.project01_meetingapp.ui.widget.CustomButton
import kotlinx.coroutines.launch

@Composable
fun GroupDetailScreen(viewModel: GroupViewModel, navigateToUserProfile: (String) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        AsyncImage(
            model = uiState.groupDetail.image,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(uiState.groupDetail.title, style = Typography.bodyLarge)
            Spacer(Modifier.height(8.dp))

            Text(uiState.groupDetail.information, style = Typography.bodyMedium)
            Spacer(Modifier.height(16.dp))

            if (uiState.members.none { uiState.profile.id == it.id }) {
                CustomButton("모임 가입", onClick = {
                    scope.launch {
                        viewModel.event.send(GroupContract.Event.Join)
                    }
                })
                Spacer(Modifier.height(16.dp))
            }
            Text(
                "정모 일정",
                style = Typography.bodyLarge,
            )

            Spacer(Modifier.height(8.dp))

            if (uiState.moims.isEmpty()) {
                Text(
                    "현재 예정된 정모 일정이 없습니다",
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Column {
                    uiState.moims.forEach { moimItem ->
                        MoimCard(moimItem)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "모임 멤버",
                style = Typography.bodyLarge,
            )
            Spacer(Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                uiState.members.forEach { member ->
                    MemberCard(member, member.id == uiState.groupDetail.masterId) {
                        navigateToUserProfile(member.id)
                    }
                }
            }
        }
    }
}

@Composable
fun MoimCard(moimItem: MoimItem) {

}

@Composable
fun MemberCard(member: Profile, isMaster: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Container,
        ),
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .fillMaxWidth()
        ) {
            Row(
            ) {
                AsyncImage(
                    model = member.thumb,
                    contentDescription = "thumb",
                    placeholder = painterResource(R.drawable.placeholder),
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(80.dp)
                        .aspectRatio(1f),
                )

                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(member.name, style = Typography.bodyMedium)

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(member.message, style = Typography.bodySmall)
                }
            }

            if (isMaster) {
                Text("모임장", modifier = Modifier.align(Alignment.TopEnd), style = Typography.bodyMedium, color = Red02)
            }
        }

    }
}