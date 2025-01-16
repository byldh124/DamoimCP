package com.moondroid.project01_meetingapp.ui.features.common.setting.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moondroid.project01_meetingapp.ui.features.common.setting.SettingContract
import com.moondroid.project01_meetingapp.ui.features.common.setting.SettingViewModel
import com.moondroid.project01_meetingapp.ui.theme.Gray03
import com.moondroid.project01_meetingapp.ui.theme.Red02
import com.moondroid.project01_meetingapp.ui.widget.BaseLayout
import com.moondroid.project01_meetingapp.ui.widget.CustomButton
import com.moondroid.project01_meetingapp.ui.widget.CustomDialog
import com.moondroid.project01_meetingapp.ui.widget.CustomDialog2
import com.moondroid.project01_meetingapp.ui.widget.DialogButton
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(navigateToSign: () -> Unit, navigateUp: () -> Unit) {
    val viewModel = hiltViewModel<SettingViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect {
            when (it) {
                SettingContract.Effect.NavigateToSign -> navigateToSign()
            }
        }
    }

    val showLogoutDialog = remember { mutableStateOf(false) }
    val showResignDialog = remember { mutableStateOf(false) }


    BaseLayout(
        "설정", navigateUp
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CustomButton(
                "로그아웃", containerColor = Red02,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                showLogoutDialog.value = true
            }

            CustomButton(
                "회원탈퇴",
                containerColor = Gray03,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                showResignDialog.value = true
            }
        }

        if (showLogoutDialog.value) {
            CustomDialog2("로그아웃 하시겠습니까?", null, DialogButton("로그아웃") {
                showLogoutDialog.value = false
                scope.launch {
                    viewModel.event.send(SettingContract.Event.Logout)
                }
            }, DialogButton("취소") {
                showLogoutDialog.value = false
            })
        }

        if (showResignDialog.value) {
            CustomDialog2("정말 회원탈퇴 하시겠습니까?", null, DialogButton("회원탈퇴") {
                showResignDialog.value = false
                scope.launch {
                    viewModel.event.send(SettingContract.Event.Resign)
                }
            }, DialogButton("취소") {
                showResignDialog.value = false
            })
        }

        if (uiState is SettingContract.State.Loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        if (uiState is SettingContract.State.Error) {
            CustomDialog((uiState as SettingContract.State.Error).message) {
                scope.launch {
                    viewModel.event.send(SettingContract.Event.Retry)
                }
            }
        }
    }
}