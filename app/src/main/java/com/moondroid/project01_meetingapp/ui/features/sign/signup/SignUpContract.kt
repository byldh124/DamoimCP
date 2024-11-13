package com.moondroid.project01_meetingapp.ui.features.sign.signup

import androidx.navigation.NavOptions
import com.moondroid.damoim.common.Constants.DEFAULT_PROFILE_IMG
import com.moondroid.project01_meetingapp.core.navigation.Destination
import com.moondroid.project01_meetingapp.core.base.UiEffect
import com.moondroid.project01_meetingapp.core.base.UiEvent
import com.moondroid.project01_meetingapp.core.base.UiState

class SignUpContract {
    data class State(
        val id: String = "",
        val pw: String = "",
        val confirmPw: String = "",
        val name: String = "",
        val gender: String = "",
        val thumb: String = DEFAULT_PROFILE_IMG,
        val birth: String = "",
        val location: String = "",
        val interest: String = "",
        val policyAgree: Boolean = false,
        val errorMessage: String = "",
        val concrete: Concrete = Concrete.Idle,
    ) : UiState {
        enum class Concrete {
            Idle, Loading, Error
        }
    }

    sealed interface Event : UiEvent {
        data class PutId(val id: String) : Event
        data class PutPw(val pw: String) : Event
        data class PutConfirmPw(val confirmPw: String) : Event
        data class PutName(val name: String) : Event
        data class PutGender(val gender: String) : Event
        data class PutThumb(val thumb: String) : Event
        data class PutBirth(val birth: String) : Event
        data class PutPolicyAgree(val policyAgree: Boolean) : Event
        data class SignUp(val location: String, val interest: String): Event
        data object Retry: Event
    }

    sealed interface Effect : UiEffect {
        data class Navigate(val destination: Destination, val navOptions: NavOptions? = null) : Effect
    }
}