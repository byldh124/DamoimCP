package com.moondroid.project01_meetingapp.ui.features.sign.signup

import com.moondroid.damoim.common.Constants.DEFAULT_PROFILE_IMG
import com.moondroid.project01_meetingapp.core.UiEffect
import com.moondroid.project01_meetingapp.core.UiEvent
import com.moondroid.project01_meetingapp.core.UiState

class SignUpContract {
    data class State(
        val id : String,
        val pw : String,
        val confirmPw: String,
        val name: String,
        val gender : String,
        val thumb: String = DEFAULT_PROFILE_IMG,
        val birth: String,
        val location: String,
        val interest: String,
        val toAgree: Boolean = false,
        val errorMessage: String = "",
        val concrete: Concrete = Concrete.Idle
    ): UiState {
        enum class Concrete {
            Idle, Loading, Error
        }
    }


    sealed interface Event : UiEvent{
        data class PutId(val id: String): Event
    }
    sealed interface Effect : UiEffect
}