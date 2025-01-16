package com.moondroid.project01_meetingapp.ui.features.common.setting

import com.moondroid.project01_meetingapp.core.base.UiEffect
import com.moondroid.project01_meetingapp.core.base.UiEvent
import com.moondroid.project01_meetingapp.core.base.UiState

interface SettingContract {
    sealed interface State : UiState{
        data object Idle: State
        data object Loading: State
        data class Error(val message: String): State
    }

    sealed interface Event: UiEvent {
        data object Logout: Event
        data object Resign: Event
        data object Retry: Event
    }

    sealed interface Effect: UiEffect {
        data object NavigateToSign: Effect
    }
}