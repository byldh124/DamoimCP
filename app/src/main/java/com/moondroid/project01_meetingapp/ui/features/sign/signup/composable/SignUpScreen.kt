@file:OptIn(ExperimentalMaterial3Api::class)

package com.moondroid.project01_meetingapp.ui.features.sign.signup.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import com.moondroid.project01_meetingapp.core.Destination
import com.moondroid.project01_meetingapp.ui.features.sign.signup.SignUpViewModel
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import com.moondroid.project01_meetingapp.ui.widget.CustomButton
import com.moondroid.project01_meetingapp.ui.widget.CustomTextField

@Composable
fun SignUpScreen(socialSignData: SocialSignData? = null, navigate: (Destination, NavOptions?) -> Unit, back: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("회원가입") },
                navigationIcon = {
                    IconButton(back) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            SignUpContent()
        }
    }
}

@Composable
fun SignUpContent() {
    val viewModel : SignUpViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column {
        //CustomTextField()
    }
}
