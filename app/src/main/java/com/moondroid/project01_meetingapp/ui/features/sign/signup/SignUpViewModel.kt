package com.moondroid.project01_meetingapp.ui.features.sign.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.moondroid.damoim.common.constant.DMRegex
import com.moondroid.damoim.common.util.byteToString
import com.moondroid.damoim.common.util.hashingPw
import com.moondroid.damoim.common.util.simpleName
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.profile.UpdateTokenUseCase
import com.moondroid.damoim.domain.usecase.sign.SignUpUseCase
import com.moondroid.project01_meetingapp.core.base.BaseViewModel
import com.moondroid.project01_meetingapp.core.navigation.SignUp
import com.moondroid.project01_meetingapp.core.navigation.SocialSignDataType
import com.moondroid.project01_meetingapp.ui.features.sign.signup.SignUpContract.Effect
import com.moondroid.project01_meetingapp.ui.features.sign.signup.SignUpContract.Event
import com.moondroid.project01_meetingapp.ui.features.sign.signup.SignUpContract.State
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.SecureRandom
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<State, Event, Effect>(State()) {

    private val _isSocialSign = MutableStateFlow(false)
    val isSocialSign = _isSocialSign.asStateFlow()

    init {
        val data =
            savedStateHandle.toRoute<SignUp>(typeMap = mapOf(typeOf<SocialSignData>() to SocialSignDataType)).socialSignData
        if (data.isNotEmpty()) {
            setState { copy(id = data.id, pw = data.id, confirmPw = data.id, thumb = data.thumb, name = data.name) }
        }
        _isSocialSign.value = data.isNotEmpty()
    }

    override suspend fun handleEvent(event: Event) {
        when (event) {
            is Event.PutId -> setState { copy(id = event.id) }
            is Event.PutPw -> setState { copy(pw = event.pw) }
            is Event.PutConfirmPw -> setState { copy(confirmPw = event.confirmPw) }
            is Event.PutThumb -> setState { copy(thumb = event.thumb) }
            is Event.PutName -> setState { copy(name = event.name) }
            is Event.PutBirth -> setState { copy(birth = event.birth) }
            is Event.PutGender -> setState { copy(gender = event.gender) }
            is Event.PutInterest -> setState { copy(interest = event.interest) }
            is Event.PutLocation -> setState { copy(location = event.location) }
            is Event.PutPolicyAgree -> setState { copy(policyAgree = event.policyAgree) }
            is Event.SignUp -> checkValidation()
            Event.Retry -> setState { copy(errorMessage = "", concrete = State.Concrete.Idle) }
        }
    }

    private fun checkValidation() {
        val id = uiState.value.id
        val pw = uiState.value.pw
        val confirmPw = uiState.value.confirmPw
        val thumb = uiState.value.thumb
        val name = uiState.value.name
        val birth = uiState.value.birth
        val gender = uiState.value.gender
        val interest = uiState.value.interest
        val location = uiState.value.location
        val policyAgree = uiState.value.policyAgree

        setState { copy(concrete = State.Concrete.Loading) }

        if (!_isSocialSign.value && !id.matches(DMRegex.ID)) {
            setState { copy(errorMessage = "아이디 에러", concrete = State.Concrete.Error) }
        } else if (!_isSocialSign.value && !pw.matches(DMRegex.PW)) {
            setState { copy(errorMessage = "비밀번호 에러", concrete = State.Concrete.Error) }
        } else if (pw != confirmPw) {
            setState { copy(errorMessage = "비밀번호 다름", concrete = State.Concrete.Error) }
        } else if (!_isSocialSign.value && name.isEmpty()) {
            setState { copy(errorMessage = "이름을 입력해주세요.", concrete = State.Concrete.Error) }
        } else if (interest.isEmpty()) {
            setState { copy(errorMessage = "관심사를 선택해주세요.", concrete = State.Concrete.Error) }
        } else if (location.isEmpty()) {
            setState { copy(errorMessage = "관심지역을 선택해주세요.", concrete = State.Concrete.Error) }
        } else if (!policyAgree) {
            setState { copy(errorMessage = "이용약관에 동의해주세요.", concrete = State.Concrete.Error) }
        } else {
            val salt = getSalt()
            val hashPw = hashingPw(pw, salt)
            signup(id, hashPw, salt, name, birth, gender, location, interest, thumb)
        }
    }

    private fun getSalt(): String {
        val rnd = SecureRandom()
        val temp = ByteArray(16)
        rnd.nextBytes(temp)
        return byteToString(temp)
    }

    private fun signup(
        id: String,
        hashPw: String,
        salt: String,
        name: String,
        birth: String,
        gender: String,
        location: String,
        interest: String,
        thumb: String,
    ) {
        viewModelScope.launch {
            signUpUseCase(
                id,
                hashPw,
                salt,
                name,
                birth,
                gender,
                location,
                interest,
                thumb
            ).collect { result ->
                result.onSuccess {
                    getMsgToken()
                }.onFail {
                    setState { copy(errorMessage = "에러 : $it", concrete = State.Concrete.Error) }
                }.onError {
                    setState { copy(errorMessage = "에러 : ${it.simpleName()}", concrete = State.Concrete.Error) }
                }
            }
        }
    }

    /**
     * FCM 토큰 생성
     * [토큰 생성되지 않은 경우에도 정상처리]
     */
    private fun getMsgToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                setEffect(Effect.NavigateToHome)
                return@OnCompleteListener
            }

            val token = task.result
            updateToken(token)
        })
    }

    /**
     * 토큰 등록
     * [토큰 미등록시에도 정상처리]
     */
    private fun updateToken(token: String) {
        viewModelScope.launch {
            updateTokenUseCase(token).collect {
                setEffect(Effect.NavigateToHome)
            }
        }
    }
}