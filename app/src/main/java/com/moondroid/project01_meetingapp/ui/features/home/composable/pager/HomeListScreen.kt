package com.moondroid.project01_meetingapp.ui.features.home.composable.pager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moondroid.project01_meetingapp.ui.features.group.list.GroupListScreen
import com.moondroid.project01_meetingapp.ui.features.home.HomeContract
import com.moondroid.project01_meetingapp.ui.features.home.HomeViewModel
import com.moondroid.project01_meetingapp.ui.theme.Red02
import com.moondroid.project01_meetingapp.ui.widget.ButtonDialog
import com.moondroid.project01_meetingapp.ui.widget.LoadingDialog
import com.moondroid.project01_meetingapp.ui.widget.PositiveButton
import kotlinx.coroutines.launch

@Composable
fun HomeListScreen(viewModel: HomeViewModel, toGroupDetail: (String) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.event.send(HomeContract.Event.Fetch())
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        GroupListScreen(uiState.list) {
            toGroupDetail(it.title)
        }

        if (uiState.concrete == HomeContract.State.Concrete.Loading) {
            LoadingDialog()
        }

        if (uiState.concrete == HomeContract.State.Concrete.Error) {
            ButtonDialog(PositiveButton {
                scope.launch {
                    viewModel.event.send(uiState.retryType)
                }
            }) {
                Text(uiState.errorMessage)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            FloatingActionButton(
                onClick = {

                },
                shape = CircleShape,
                containerColor = Red02,
            ) {
                Icon(Icons.Rounded.Add, "add_group", tint = Color.White)

            }
        }
    }
}