package com.moondroid.project01_meetingapp.ui.features.sign.signin

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.DMRegex
import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.common.hashingPw
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.sign.SaltUseCase
import com.moondroid.damoim.domain.usecase.sign.SignInUseCase
import com.moondroid.damoim.domain.usecase.sign.SocialSignUseCase
import com.moondroid.project01_meetingapp.core.base.BaseViewModel
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val saltUseCase: SaltUseCase,
    private val signInUseCase: SignInUseCase,
    private val socialSignUseCase: SocialSignUseCase,
) : BaseViewModel<SignInContract.State, SignInContract.Event, SignInContract.Effect>(SignInContract.State()) {
    override suspend fun handleEvent(event: SignInContract.Event) {
        when (event) {
            is SignInContract.Event.SocialSign -> signInSocial(event.socialSignData)
            is SignInContract.Event.IdChanged -> setState { copy(id = event.id) }
            is SignInContract.Event.PwChanged -> setState { copy(pw = event.pw) }
            SignInContract.Event.Sign -> checkValid()
            SignInContract.Event.Retry -> setState {
                copy(
                    errorMessage = "",
                    concrete = SignInContract.State.Concrete.Idle
                )
            }
        }
    }

    private fun checkValid() {
        val id = uiState.value.id
        val pw = uiState.value.pw

        if (!id.matches(DMRegex.ID)) {
            setState { copy(errorMessage = "아이디 오류", concrete = SignInContract.State.Concrete.Error) }
        } else if (!pw.matches(DMRegex.PW)) {
            setState { copy(errorMessage = "비밀번호 오류", concrete = SignInContract.State.Concrete.Error) }
        } else {
            getSalt(id, pw)
        }
    }

    private fun getSalt(id: String, pw: String) {
        setState { copy(concrete = SignInContract.State.Concrete.Loading) }
        viewModelScope.launch {
            delay(1000)
            saltUseCase(id).collect { result ->
                result.onSuccess {
                    val hashPw = hashingPw(pw, it)
                    signIn(id, hashPw)
                }.onFail {
                    setState { copy(concrete = SignInContract.State.Concrete.Error, errorMessage = "로그인 실패 [$it]") }
                }.onError {
                    setState {
                        copy(
                            concrete = SignInContract.State.Concrete.Error,
                            errorMessage = it.javaClass.simpleName
                        )
                    }
                }
            }
        }
    }

    private suspend fun signIn(id: String, pw: String) {
        signInUseCase(id, pw).collect { result ->
            setState { copy(concrete = SignInContract.State.Concrete.Loading) }
            result.onSuccess {
                setEffect(SignInContract.Effect.NavigateToHome)
            }.onFail {
                setState { copy(concrete = SignInContract.State.Concrete.Error, errorMessage = "로그인 실패 [$it]") }
            }.onError {
                setState {
                    copy(
                        concrete = SignInContract.State.Concrete.Error,
                        errorMessage = it.javaClass.simpleName
                    )
                }
            }
        }
    }

    /**
     * 카카오 로그인
     * [a] 기존 데이터 o : 회원 정보 요청
     * [b] 기존 데이터 x : 회원가입 화면 전환
     */
    private fun signInSocial(socialSignData: SocialSignData) {
        viewModelScope.launch(Dispatchers.IO) {
            socialSignUseCase(socialSignData.id).collect { result ->
                result.onSuccess {
                    setEffect(SignInContract.Effect.NavigateToHome)
                }.onFail {
                    if (it == ResponseCode.NOT_EXIST) {
                        setEffect(SignInContract.Effect.NavigateToSignUp(socialSignData))
                    }
                }
            }
        }
    }
}