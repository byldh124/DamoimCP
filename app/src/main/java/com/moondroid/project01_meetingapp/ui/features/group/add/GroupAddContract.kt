package com.moondroid.project01_meetingapp.ui.features.group.add

import android.net.Uri
import com.moondroid.project01_meetingapp.core.base.UiEffect
import com.moondroid.project01_meetingapp.core.base.UiEvent
import com.moondroid.project01_meetingapp.core.base.UiState

interface GroupAddContract {
    data class State(
        val title: String = "",
        val location: String = "",
        val interest: String = "",
        val purpose: String = "",
        val uri: Uri? = null,
        val errorMessage: String = "",
        val concrete: Concrete = Concrete.Idle,
    ) : UiState {
        enum class Concrete {
            Idle, Error, Loading, Success
        }
    }

    sealed interface Event : UiEvent {
        data class PutTitle(val title: String) : Event
        data class PutLocation(val location: String) : Event
        data class PutInterest(val interest: String) : Event
        data class PutPurpose(val purpose: String) : Event
        data class PutUri(val uri: Uri) : Event
        data object RESET : Event
        data class Add(val path: String) : Event
    }

    sealed interface Effect : UiEffect {
        data class NavigateToGroup(val id: String) : Effect
    }
}