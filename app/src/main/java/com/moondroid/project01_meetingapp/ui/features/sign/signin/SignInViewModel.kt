package com.moondroid.project01_meetingapp.ui.features.sign.signin

import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.moondroid.damoim.common.Extension.hashingPw
import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.sign.SaltUseCase
import com.moondroid.damoim.domain.usecase.sign.SignInUseCase
import com.moondroid.damoim.domain.usecase.sign.SocialSignUseCase
import com.moondroid.project01_meetingapp.core.BaseViewModel
import com.moondroid.project01_meetingapp.core.Home
import com.moondroid.project01_meetingapp.core.Sign
import com.moondroid.project01_meetingapp.core.SignUp
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
            SignInContract.Event.Sign -> getSalt()
            SignInContract.Event.Retry -> setState {copy(errorMessage = "", concrete = SignInContract.State.Concrete.Idle)}
        }
    }

    private suspend fun getSalt() {
        setState { copy(concrete = SignInContract.State.Concrete.Loading) }
        delay(1000)
        val id = uiState.value.id
        val pw = uiState.value.pw
        saltUseCase(id).collect { result ->
            result.onSuccess {
                val hashPw = hashingPw(pw, it)
                signIn(id, hashPw)
            }.onFail {
                setState { copy(concrete = SignInContract.State.Concrete.Error) }
            }.onError {
                setState { copy(concrete = SignInContract.State.Concrete.Error) }
            }
        }
    }

    private suspend fun signIn(id: String, pw: String) {
        signInUseCase(id, pw).collect { result ->
            setState { copy(concrete = SignInContract.State.Concrete.Loading) }
            result.onSuccess {
                setEffect(SignInContract.Effect.Navigate(Home, navOptions {
                    popUpTo(Sign) { inclusive = true }
                }))
            }.onFail {
                setState { copy(concrete = SignInContract.State.Concrete.Error) }
            }.onError {
                setState { copy(concrete = SignInContract.State.Concrete.Error) }
            }
        }
    }

    /**
     * 카카오 로그인
     * [a] 기존 데이터 o : 회원 정보 요청
     * [b] 기존 데이터 x : 회원가입 화면 전환
     */
    fun signInSocial(socialSignData: SocialSignData) {
        viewModelScope.launch(Dispatchers.IO) {
            socialSignUseCase(socialSignData.id).collect { result ->
                result.onSuccess {
                    setEffect(SignInContract.Effect.Navigate(Home, navOptions {
                        popUpTo(Sign) { inclusive = true }
                    }))
                }.onFail {
                    if (it == ResponseCode.NOT_EXIST) {
                        setEffect(SignInContract.Effect.Navigate(SignUp(socialSignData), null))
                    }
                }
            }
        }
    }
}