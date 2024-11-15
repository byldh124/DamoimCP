package com.moondroid.project01_meetingapp.ui.features.home

import androidx.navigation.NavOptions
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.core.base.UiEffect
import com.moondroid.project01_meetingapp.core.base.UiEvent
import com.moondroid.project01_meetingapp.core.base.UiState
import com.moondroid.project01_meetingapp.core.navigation.Destination

interface HomeContract {
    data class State(
        val profile: Profile? = null,
        val list: List<GroupItem> = emptyList(),
        val errorMessage: String = "",
        val retryType: Event = Event.Fetch,
        val concrete: Concrete = Concrete.Idle,
    ) : UiState {
        enum class Concrete {
            Idle, Loading, Error
        }
    }

    sealed interface Event : UiEvent {
        data object GetProfile : Event
        data object Fetch : Event
    }

    sealed interface Effect : UiEffect {
        data class Navigate(val d: Destination, val options: NavOptions) : Effect
    }
}