package com.moondroid.project01_meetingapp.ui.features.sign.signup

import com.moondroid.project01_meetingapp.core.UiEffect
import com.moondroid.project01_meetingapp.core.UiEvent
import com.moondroid.project01_meetingapp.core.UiState

class SignUpContract {
    sealed interface State : UiState {
        data object IDLE : State
    }

    sealed interface Event : UiEvent
    sealed interface Effect : UiEffect
}