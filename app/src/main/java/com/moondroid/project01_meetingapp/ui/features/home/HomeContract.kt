package com.moondroid.project01_meetingapp.ui.features.home

import com.moondroid.damoim.common.constant.GroupType
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.core.base.UiEffect
import com.moondroid.project01_meetingapp.core.base.UiEvent
import com.moondroid.project01_meetingapp.core.base.UiState

interface HomeContract {
    data class State(
        val profile: Profile? = null,
        val list: List<GroupItem> = emptyList(),
        val errorMessage: String = "",
        val retryType: Event = Event.Fetch(),
        val concrete: Concrete = Concrete.Idle,
    ) : UiState {
        enum class Concrete {
            Idle, Loading, Error
        }
    }

    sealed interface Event : UiEvent {
        data object GetProfile : Event
        data class Fetch(val groupType: GroupType = GroupType.ALL) : Event
    }

    sealed interface Effect : UiEffect {
        data object Expired : Effect
    }
}