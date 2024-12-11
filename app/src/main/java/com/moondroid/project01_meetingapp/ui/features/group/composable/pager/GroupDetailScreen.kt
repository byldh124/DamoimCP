package com.moondroid.project01_meetingapp.ui.features.group.composable.pager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.moondroid.project01_meetingapp.ui.features.group.GroupViewModel

@Composable
fun GroupDetailScreen(viewModel: GroupViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
    }
}