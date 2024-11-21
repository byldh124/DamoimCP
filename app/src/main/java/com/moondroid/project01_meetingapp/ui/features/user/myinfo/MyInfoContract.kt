package com.moondroid.project01_meetingapp.ui.features.user.myinfo

import android.net.Uri
import com.moondroid.project01_meetingapp.core.base.UiEffect
import com.moondroid.project01_meetingapp.core.base.UiEvent
import com.moondroid.project01_meetingapp.core.base.UiState

interface MyInfoContract {
    data class State(
        val name: String = "",
        val birth: String = "",
        val gender: String = "",
        val location: String = "",
        val message: String = "",
        val imageUrl: String = "",
        val uri: Uri? = null,
        val errorMessage: String = "",
        val concrete: Concrete = Concrete.Idle,
    ) : UiState {
        enum class Concrete {
            Idle, Error, Loading, Success
        }
    }

    sealed interface Event : UiEvent {
        data class PutName(val name: String) : Event
        data class PutBirth(val birth: String) : Event
        data class PutLocation(val location: String) : Event
        data class PutGender(val gender: String) : Event
        data class PutMessage(val message: String) : Event
        data class PutUri(val uri: Uri) : Event
        data object Modify: Event
    }

    sealed interface Effect : UiEffect
}