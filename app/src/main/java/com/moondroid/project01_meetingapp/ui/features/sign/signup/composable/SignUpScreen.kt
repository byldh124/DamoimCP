package com.moondroid.project01_meetingapp.ui.features.sign.signup.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import com.moondroid.project01_meetingapp.core.navigation.Destination
import com.moondroid.project01_meetingapp.core.navigation.InterestList
import com.moondroid.project01_meetingapp.core.navigation.LocationList
import com.moondroid.project01_meetingapp.ui.features.sign.signup.SignUpContract
import com.moondroid.project01_meetingapp.ui.features.sign.signup.SignUpViewModel
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import com.moondroid.project01_meetingapp.ui.theme.Gray03
import com.moondroid.project01_meetingapp.ui.theme.Typography
import com.moondroid.project01_meetingapp.ui.widget.BaseLayout
import com.moondroid.project01_meetingapp.ui.widget.CustomButton
import com.moondroid.project01_meetingapp.ui.widget.CustomDialog
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
    val viewModel: SignUpViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BaseLayout(
        title = "회원가입",
        onBack = navigateUp
    ) {
        SignUpContent(viewModel, uiState, socialSignData, interest, location, navigate)

        if (uiState.concrete == SignUpContract.State.Concrete.Loading) {
            Dialog({}) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        if (uiState.concrete == SignUpContract.State.Concrete.Error) {
            CustomDialog(onDismiss = {
                scope.launch {
                    viewModel.event.send(SignUpContract.Event.Retry)
                }
            }, uiState.errorMessage, "에러", "재시도")
        }
    }
}

@Composable
fun SignUpContent(
    viewModel: SignUpViewModel,
    uiState: SignUpContract.State,
    socialSignData: SocialSignData,
    interest: String,
    location: String,
    navigate: (Destination, NavOptions?) -> Unit,
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect {
            when (it) {
                is SignUpContract.Effect.Navigate -> navigate(it.destination, it.navOptions)
            }
        }

        if (!socialSignData.isEmpty()) {
            viewModel.event.send(SignUpContract.Event.PutId(socialSignData.id))
            viewModel.event.send(SignUpContract.Event.PutPw(socialSignData.id))
            viewModel.event.send(SignUpContract.Event.PutConfirmPw(socialSignData.id))
            viewModel.event.send(SignUpContract.Event.PutName(socialSignData.name))
            viewModel.event.send(SignUpContract.Event.PutThumb(socialSignData.thumb))
        }
    }


    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        if (socialSignData.isEmpty()) {
            CustomTextField(
                uiState.id,
                onTextChanged = {
                    scope.launch {
                        viewModel.event.send(SignUpContract.Event.PutId(it))
                    }
                },
                label = "아이디"
            )


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

        CustomText(uiState.birth.ifEmpty { "생년월일" }) {

        }

        CustomText(location.ifEmpty { "관심지역" }) {
            navigate(LocationList, null)
        }

        CustomText(interest.ifEmpty { "관심사" }) {
            navigate(InterestList, null)
        }

        Spacer(Modifier.height(20.dp))

        CustomButton("회원가입", onClick = {
            scope.launch {
                viewModel.event.send(SignUpContract.Event.SignUp(location, interest))
            }
        })
    }
}

@Composable
fun CustomText(text: String, onClick: () -> Unit) {
    Box(modifier = Modifier.padding(vertical = 5.dp)) {
        Text(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Gray03,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 18.dp),
            text = text,
            style = Typography.bodyMedium
        )
    }
}