package com.moondroid.project01_meetingapp.ui.features.sign.signin

import com.moondroid.project01_meetingapp.core.base.UiEffect
import com.moondroid.project01_meetingapp.core.base.UiEvent
import com.moondroid.project01_meetingapp.core.base.UiState
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData

class SignInContract {
    data class State(
        val id: String = "",
        val pw: String = "",
        val errorMessage: String = "",
        val concrete: Concrete = Concrete.Idle,
    ) : UiState {
        enum class Concrete {
            Idle, Loading, Error
        }
    }

    sealed interface Event : UiEvent {
        data class IdChanged(val id: String) : Event
        data class PwChanged(val pw: String) : Event
        data object Sign: Event
        data object Retry: Event
        data class SocialSign(val socialSignData: SocialSignData) : Event
        data class SocialSignFail(val throwable: Throwable) : Event
    }

    sealed interface Effect : UiEffect {
        data object NavigateToHome : Effect
        data class NavigateToSignUp(val socialSignData: SocialSignData) : Effect
    }
}