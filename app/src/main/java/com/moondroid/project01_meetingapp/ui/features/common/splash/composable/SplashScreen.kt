package com.moondroid.project01_meetingapp.ui.features.common.splash.composable

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moondroid.damoim.common.util.logException
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.core.navigation.Destination
import com.moondroid.project01_meetingapp.ui.features.common.splash.SplashContract
import com.moondroid.project01_meetingapp.ui.features.common.splash.SplashViewModel
import com.moondroid.project01_meetingapp.ui.theme.Red01
import com.moondroid.project01_meetingapp.ui.widget.CustomDialog
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navigate: (Destination) -> Unit, navigateUp: () -> Unit) {
    val mContext = LocalContext.current
    val lifecycleScope = rememberCoroutineScope()
    val viewModel: SplashViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val notSupportDialogShow = remember { mutableStateOf(false) }
    val updateDialogShow = remember { mutableStateOf(false) }


    LaunchedEffect(viewModel) {
        viewModel.event.send(SplashContract.Event.CheckAppVersion)
        viewModel.effect.collect {
            when (it) {
                is SplashContract.Effect.Navigate -> navigate(it.destination)
                SplashContract.Effect.NotSupport -> notSupportDialogShow.value = true
                SplashContract.Effect.Update -> updateDialogShow.value = true
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (val state = uiState) {
            SplashContract.State.Idle -> {
                Text(stringResource(R.string.app_name), color = Red01, fontSize = 20.sp)
                Text("우리들의 모임앱")
            }

            is SplashContract.State.Fail -> {
                Text("오류")
                Text(state.message)
                Button({
                    lifecycleScope.launch {
                        viewModel.event.send(SplashContract.Event.CheckAppVersion)
                    }
                }) {
                    Text("재시도")
                }
            }
        }
        if (notSupportDialogShow.value) {
            CustomDialog("해당버전이 존재하지 않습니다.") {
                navigateUp()
            }
        }

        if (updateDialogShow.value) {
            CustomDialog("업데이트가 필요합니다.") {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("market://details?id=${mContext.packageName}")
                    mContext.startActivity(intent)
                } catch (e: Exception) {
                    logException(e)
                }
            }
        }
    }
}
