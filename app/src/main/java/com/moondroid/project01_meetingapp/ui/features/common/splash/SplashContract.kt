package com.moondroid.project01_meetingapp.ui.features.common.splash

import com.moondroid.project01_meetingapp.core.navigation.Destination
import com.moondroid.project01_meetingapp.core.base.UiEffect
import com.moondroid.project01_meetingapp.core.base.UiEvent
import com.moondroid.project01_meetingapp.core.base.UiState

interface SplashContract {
    sealed interface State : UiState {
        data object Idle : State
        data class Fail(val message: String) : State
    }

    sealed interface Event : UiEvent {
        data object CheckAppVersion : Event
    }

    sealed interface Effect : UiEffect {
        data class Navigate(val destination: Destination) : Effect
        data object Update : Effect
        data object NotSupport : Effect
    }
}