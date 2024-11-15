package com.moondroid.project01_meetingapp.ui.features.home.composable.pager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moondroid.project01_meetingapp.ui.features.group.list.GroupListScreen
import com.moondroid.project01_meetingapp.ui.features.home.HomeContract
import com.moondroid.project01_meetingapp.ui.features.home.HomeViewModel
import com.moondroid.project01_meetingapp.ui.widget.CustomDialog
import kotlinx.coroutines.launch

@Composable
fun HomeListScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.event.send(HomeContract.Event.Fetch())
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        GroupListScreen(uiState.list) {}

        if (uiState.concrete == HomeContract.State.Concrete.Loading) {
            Dialog({}) {
                CircularProgressIndicator()
            }
        }

        if (uiState.concrete == HomeContract.State.Concrete.Error) {
            CustomDialog({
                scope.launch {
                    viewModel.event.send(uiState.retryType)
                }
            }, content = uiState.errorMessage)
        }
    }
}