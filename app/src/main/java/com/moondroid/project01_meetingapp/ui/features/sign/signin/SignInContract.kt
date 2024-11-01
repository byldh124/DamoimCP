package com.moondroid.project01_meetingapp.ui.features.sign.signin

import androidx.navigation.NavOptions
import com.moondroid.project01_meetingapp.core.Destination
import com.moondroid.project01_meetingapp.core.UiEffect
import com.moondroid.project01_meetingapp.core.UiEvent
import com.moondroid.project01_meetingapp.core.UiState
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
    }

    sealed interface Effect : UiEffect {
        data class Navigate(val destination: Destination, val navOptions: NavOptions? = null) : Effect
    }
}