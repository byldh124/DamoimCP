@file:OptIn(ExperimentalMaterial3Api::class)

package com.moondroid.project01_meetingapp.ui.features.sign.signup.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import com.moondroid.project01_meetingapp.core.Destination
import com.moondroid.project01_meetingapp.core.InterestList
import com.moondroid.project01_meetingapp.ui.features.sign.signup.SignUpContract
import com.moondroid.project01_meetingapp.ui.features.sign.signup.SignUpViewModel
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import com.moondroid.project01_meetingapp.ui.widget.CustomTextField
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    socialSignData: SocialSignData,
    interest: String = "",
    location: String = "",
    navigate: (Destination, NavOptions?) -> Unit,
    navigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("회원가입") },
                navigationIcon = {
                    IconButton(navigateUp) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            SignUpContent(socialSignData, interest, location, navigate)
        }
    }
}

@Composable
fun SignUpContent(
    socialSignData: SocialSignData,
    interest: String,
    location: String,
    navigate: (Destination, NavOptions?) -> Unit,
) {
    val viewModel: SignUpViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.effect.collect {
            when (it) {
                is SignUpContract.Effect.Navigate -> navigate(it.destination, it.navOptions)
            }
        }
    }
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        CustomTextField(uiState.id, onTextChanged = {
            scope.launch {
                viewModel.event.send(SignUpContract.Event.PutId(it))
            }
        }, label = "아이디")

        CustomTextField(
            uiState.pw,
            onTextChanged = {
                scope.launch {
                    viewModel.event.send(SignUpContract.Event.PutPw(it))
                }
            },
            label = "비밀번호",
            visualTransformation = PasswordVisualTransformation()
        )

        CustomTextField(
            uiState.confirmPw,
            onTextChanged = {
                scope.launch {
                    viewModel.event.send(SignUpContract.Event.PutConfirmPw(it))
                }
            },
            label = "비밀번호 확인",
            visualTransformation = PasswordVisualTransformation()
        )

        CustomTextField(
            uiState.name,
            onTextChanged = {
                scope.launch {
                    viewModel.event.send(SignUpContract.Event.PutName(it))
                }
            },
            label = "닉네임",
        )
    }
}
