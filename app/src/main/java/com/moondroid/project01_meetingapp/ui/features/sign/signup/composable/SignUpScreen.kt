package com.moondroid.project01_meetingapp.ui.features.sign.signup.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import com.moondroid.project01_meetingapp.core.navigation.Destination
import com.moondroid.project01_meetingapp.core.navigation.InterestList
import com.moondroid.project01_meetingapp.core.navigation.LocationList
import com.moondroid.project01_meetingapp.ui.features.sign.signup.SignUpContract
import com.moondroid.project01_meetingapp.ui.features.sign.signup.SignUpViewModel
import com.moondroid.project01_meetingapp.ui.theme.Red01
import com.moondroid.project01_meetingapp.ui.widget.BaseLayout
import com.moondroid.project01_meetingapp.ui.widget.CustomButton
import com.moondroid.project01_meetingapp.ui.widget.CustomDialog
import com.moondroid.project01_meetingapp.ui.widget.CustomText
import com.moondroid.project01_meetingapp.ui.widget.CustomTextField
import com.moondroid.project01_meetingapp.ui.widget.DatePickerModal
import com.moondroid.project01_meetingapp.ui.widget.GenderRadioButton
import com.moondroid.project01_meetingapp.ui.widget.LoadingDialog
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    interestFlow: StateFlow<String>,
    locationFlow: StateFlow<String>,
    navigateToInterest: () -> Unit,
    navigateToLocation: () -> Unit,
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit,
) {
    val viewModel: SignUpViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isSocialSign by viewModel.isSocialSign.collectAsStateWithLifecycle()
    val interest by interestFlow.collectAsStateWithLifecycle()
    val location by locationFlow.collectAsStateWithLifecycle()
    var showDateModal by remember { mutableStateOf(false) }

    LaunchedEffect(interest, location) {
        viewModel.event.send(SignUpContract.Event.PutLocation(location))
        viewModel.event.send(SignUpContract.Event.PutInterest(interest))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect {
            when (it) {
                SignUpContract.Effect.NavigateToHome -> navigateToHome()
            }
        }
    }

    BaseLayout(
        title = "회원가입",
        onBack = navigateUp
    ) {
        SignUpContent(
            viewModel = viewModel,
            uiState = uiState,
            isSocialSign = isSocialSign,
            navigateToLocation = navigateToLocation,
            navigateToInterest = navigateToInterest,
            onDatePickerClick = { showDateModal = true }
        )

        if (showDateModal) {
            DatePickerModal(uiState.birth, {
                scope.launch {
                    viewModel.event.send(SignUpContract.Event.PutBirth(it))
                }
            }) {
                showDateModal = false
            }
        }

        if (uiState.concrete == SignUpContract.State.Concrete.Loading) {
            LoadingDialog()
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
    isSocialSign: Boolean,
    navigateToLocation: () -> Unit,
    navigateToInterest: () -> Unit,
    onDatePickerClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        if (!isSocialSign) {
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

        GenderRadioButton(uiState.gender) {
            scope.launch {
                viewModel.event.send(SignUpContract.Event.PutGender(it))
            }
        }

        CustomText(uiState.birth.ifEmpty { "생년월일" }) {
            onDatePickerClick()
        }

        CustomText(uiState.location.ifEmpty { "관심지역" }, onClick = navigateToLocation)

        CustomText(uiState.interest.ifEmpty { "관심사" }, onClick = navigateToInterest)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                uiState.policyAgree, onCheckedChange = {
                    scope.launch {
                        viewModel.event.send(SignUpContract.Event.PutPolicyAgree(it))
                    }
                }, colors = CheckboxDefaults.colors(
                    uncheckedColor = Red01,
                    checkedColor = Red01
                )
            )
            Text("이용약관 및 개인정보 처리방침에 동의해주세요.")
        }

        Spacer(Modifier.height(20.dp))

        CustomButton("회원가입", onClick = {
            scope.launch {
                viewModel.event.send(SignUpContract.Event.SignUp)
            }
        })
    }
}