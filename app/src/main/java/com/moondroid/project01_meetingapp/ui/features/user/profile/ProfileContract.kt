package com.moondroid.project01_meetingapp.ui.features.user.profile

import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.core.base.UiEffect
import com.moondroid.project01_meetingapp.core.base.UiEvent
import com.moondroid.project01_meetingapp.core.base.UiState

interface ProfileContract {
    data class State(
        val profile: Profile = Profile(),
        val groupList: List<GroupItem> = emptyList(),
        val errorMessage: String = "",
        val concrete: Concrete = Concrete.Idle
    ): UiState {
        enum class Concrete {
            Idle, Error, Loading, Success
        }
    }

    sealed interface Event : UiEvent{
        data object LoadProfile: Event
    }

    sealed interface Effect: UiEffect
}