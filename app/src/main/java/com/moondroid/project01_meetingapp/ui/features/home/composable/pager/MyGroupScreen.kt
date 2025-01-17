package com.moondroid.project01_meetingapp.ui.features.home.composable.pager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moondroid.damoim.common.constant.GroupType
import com.moondroid.project01_meetingapp.ui.features.group.composable.list.GroupListScreen
import com.moondroid.project01_meetingapp.ui.features.home.HomeContract
import com.moondroid.project01_meetingapp.ui.features.home.HomeViewModel
import com.moondroid.project01_meetingapp.ui.widget.ButtonDialog
import com.moondroid.project01_meetingapp.ui.widget.LoadingDialog
import com.moondroid.project01_meetingapp.ui.widget.PositiveButton
import kotlinx.coroutines.launch

@Composable
fun MyGroupScreen(viewModel: HomeViewModel, toGroupDetail: (String) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.event.send(HomeContract.Event.Fetch(GroupType.MY_GROUP))
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        GroupListScreen(uiState.list) {
            toGroupDetail(it.title)
        }

        if (uiState.concrete == HomeContract.State.Concrete.Loading) {
            LoadingDialog()
        }

        if (uiState.concrete == HomeContract.State.Concrete.Error) {
            ButtonDialog(
                PositiveButton {
                    scope.launch {
                        viewModel.event.send(uiState.retryType)
                    }
                }
            ) {
                Text(uiState.errorMessage)
            }
        }
    }
}