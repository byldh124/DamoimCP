package com.moondroid.project01_meetingapp.ui.features.group.composable.pager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.moondroid.damoim.common.util.debug
import com.moondroid.project01_meetingapp.ui.features.group.GroupContract
import com.moondroid.project01_meetingapp.ui.features.group.GroupViewModel

@Composable
fun GroupGalleryScreen(viewModel: GroupViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.event.send(GroupContract.Event.ImageFetch)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyVerticalGrid(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
            columns = GridCells.Fixed(3),
        ) {
            items(uiState.images.size) {
                val image = uiState.images[it]
                AsyncImage(
                    modifier = Modifier
                        .padding(2.dp)
                        .aspectRatio(1.0f),
                    model = image,
                    contentDescription = "이미지",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}