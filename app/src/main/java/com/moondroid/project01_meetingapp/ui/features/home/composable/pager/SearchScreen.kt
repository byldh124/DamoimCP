package com.moondroid.project01_meetingapp.ui.features.home.composable.pager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moondroid.project01_meetingapp.ui.features.group.composable.list.GroupListScreen
import com.moondroid.project01_meetingapp.ui.features.home.HomeContract
import com.moondroid.project01_meetingapp.ui.features.home.HomeViewModel
import com.moondroid.project01_meetingapp.ui.widget.CustomDialog
import com.moondroid.project01_meetingapp.ui.widget.CustomTextField
import com.moondroid.project01_meetingapp.ui.widget.LoadingDialog
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(viewModel: HomeViewModel, toGroupDetail: (String) -> Unit) {
    val query = remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.event.send(HomeContract.Event.Fetch())
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                CustomTextField(query.value, onTextChanged = { query.value = it })
            }
            GroupListScreen(uiState.list.filter {
                it.title.contains(query.value) || it.information.contains(query.value) || it.purpose.contains(query.value)
            }) {
                toGroupDetail(it.title)
            }
        }


        if (uiState.concrete == HomeContract.State.Concrete.Loading) {
            LoadingDialog()
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