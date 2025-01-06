package com.moondroid.project01_meetingapp.ui.features.sign.signin.composable

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.ui.features.sign.signin.SignInContract
import com.moondroid.project01_meetingapp.ui.features.sign.signin.SignInViewModel
import com.moondroid.project01_meetingapp.ui.features.sign.social.GoogleSignClient
import com.moondroid.project01_meetingapp.ui.features.sign.social.KakaoSignClient
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignEventListener
import com.moondroid.project01_meetingapp.ui.theme.Red01
import com.moondroid.project01_meetingapp.ui.theme.Typography
import com.moondroid.project01_meetingapp.ui.widget.BaseLayout
import com.moondroid.project01_meetingapp.ui.widget.CustomButton
import com.moondroid.project01_meetingapp.ui.widget.CustomDialog
import com.moondroid.project01_meetingapp.ui.widget.CustomTextField
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(navigateToSignUp: (SocialSignData) -> Unit, navigateToHome: () -> Unit) {
    val mContext = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<SignInViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val socialSignEventListener = object : SocialSignEventListener {
        override fun onSuccess(socialSignData: SocialSignData) {
            scope.launch {
                viewModel.event.send(SignInContract.Event.SocialSign(socialSignData))
            }
        }

        override fun onError(throwable: Throwable) {
            scope.launch {
                viewModel.event.send(SignInContract.Event.SocialSignFail(throwable))
            }
        }
    }

    val googleSignClient = GoogleSignClient(mContext, socialSignEventListener)
    val kakaoSignClient = KakaoSignClient(mContext, socialSignEventListener)

    val credentialLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Once the account has been added, do sign in again.
        if (it.resultCode == Activity.RESULT_OK) {
            googleSignClient.getGoogleAccount(null)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect {
            when (it) {
                is SignInContract.Effect.NavigateToSignUp -> {
                    navigateToSignUp(it.socialSignData)
                }

                SignInContract.Effect.NavigateToHome -> {
                    navigateToHome()
                }
            }
        }
    }

    BaseLayout(title = "로그인") {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Text("모임대장에서 새로운 모임을 시작하세요.", style = Typography.bodyMedium)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = R.drawable.image_login,
                    contentDescription = "로그인 화면"
                )
            }
            Text("이미 모임대장의 회원이신가요?", style = Typography.bodyMedium)
            CustomTextField(
                value = uiState.id,
                onTextChanged = {
                    scope.launch {
                        viewModel.event.send(SignInContract.Event.IdChanged(it))
                    }
                },
                label = "아이디",
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            CustomTextField(
                value = uiState.pw,
                onTextChanged = {
                    scope.launch {
                        viewModel.event.send(SignInContract.Event.PwChanged(it))
                    }
                },
                label = "비밀번호",
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))

            CustomButton("로그인하기", onClick = {
                scope.launch {
                    viewModel.event.send(SignInContract.Event.Sign)
                }
            })


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AsyncImage(
                    modifier = Modifier
                        .weight(1.0f)
                        .clickable {
                            kakaoSignClient.getKakaoAccount()
                        },
                    model = R.drawable.login_kakao,
                    contentDescription = "카카오 로그인"
                )

                Spacer(modifier = Modifier.width(10.dp))

                AsyncImage(
                    modifier = Modifier
                        .weight(1.0f)
                        .clickable {
                            googleSignClient.getGoogleAccount(credentialLauncher)
                        },

                    model = R.drawable.login_google,
                    contentDescription = "구글 로그인"
                )
            }

            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 14.sp)) {
                    append("모임대장의 회원이 아니시면")
                }
                withStyle(style = SpanStyle(color = Red01)) {
                    append(" \'여기\' ")
                }
                withStyle(style = SpanStyle(fontSize = 14.sp)) {
                    append("를 눌러주세요.")
                }
            })
        }

        if (uiState.concrete == SignInContract.State.Concrete.Loading) {
            Dialog({}) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        if (uiState.concrete == SignInContract.State.Concrete.Error) {
            CustomDialog(uiState.errorMessage, "에러", "재시도") {
                scope.launch {
                    viewModel.event.send(SignInContract.Event.Retry)
                }
            }
        }
    }
}
