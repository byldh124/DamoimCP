package com.moondroid.project01_meetingapp.ui.features.group

import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.core.base.UiEffect
import com.moondroid.project01_meetingapp.core.base.UiEvent
import com.moondroid.project01_meetingapp.core.base.UiState

interface GroupContract {
    data class State(
        val groupDetail : GroupItem= GroupItem(),
        val members: List<Profile> = emptyList(),
        val moims: List<MoimItem> = emptyList(),
        val isFavor : Boolean = false,
        val errorMessage: String = "",
        val concrete: Concrete = Concrete.Idle,
    ) : UiState {
        enum class Concrete {
            Idle, Loading, Error
        }
    }

    sealed interface Event : UiEvent {
        data object Join: Event
        data object ToggleFavor: Event
        data class UserProfile(val id: String): Event
    }

    sealed interface Effect : UiEffect {
        data object Expired: Effect
    }
}
