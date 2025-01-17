package com.moondroid.project01_meetingapp.ui.features.group.main.composable.pager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.moondroid.damoim.domain.model.ChatItem
import com.moondroid.project01_meetingapp.ui.features.group.main.GroupContract
import com.moondroid.project01_meetingapp.ui.features.group.main.GroupViewModel
import com.moondroid.project01_meetingapp.ui.theme.Red02

@Composable
fun GroupChatScreen(viewModel: GroupViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.event.send(GroupContract.Event.ChatFetch)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(uiState.chats.size) {
                val item = uiState.chats[it]
                if (item.other) {
                    OtherChatCard(item)
                } else {
                    MyChatCard(item)
                }
            }
        }
    }
}

@Composable
fun MyChatCard(chatItem: ChatItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(chatItem.time)
        Column {
            Text(chatItem.name)
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Red02
                )
            ) {
                Text(text = chatItem.message, color = Color.White)
            }
        }

        AsyncImage(
            chatItem.thumb,
            "profile_thumb",
            modifier = Modifier
                .width(80.dp)
                .clip(CircleShape)
                .aspectRatio(1f)
        )
    }
}

@Composable
fun OtherChatCard(chatItem: ChatItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            chatItem.thumb,
            "profile_thumb",
            modifier = Modifier
                .width(80.dp)
                .clip(CircleShape)
                .aspectRatio(1f)
        )

        Column {
            Text(chatItem.name)
            Card(
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = chatItem.message)
            }
        }
        Text(chatItem.time)
    }
}