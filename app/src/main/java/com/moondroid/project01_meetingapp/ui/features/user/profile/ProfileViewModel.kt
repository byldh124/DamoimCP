package com.moondroid.project01_meetingapp.ui.features.user.profile

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.GetGroupUseCase
import com.moondroid.damoim.domain.usecase.group.GetUserGroupUseCase
import com.moondroid.damoim.domain.usecase.profile.UserProfileUseCase
import com.moondroid.project01_meetingapp.core.base.BaseViewModel
import com.moondroid.project01_meetingapp.core.navigation.UserProfile
import com.moondroid.project01_meetingapp.ui.features.user.profile.ProfileContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userProfileUseCase: UserProfileUseCase,
    private val getGroupUseCase: GetUserGroupUseCase,
) : BaseViewModel<State, ProfileContract.Event, ProfileContract.Effect>(State()) {
    val id: String

    init {
        val route = savedStateHandle.toRoute<UserProfile>()
        id = route.id
    }

    override suspend fun handleEvent(event: ProfileContract.Event) {
        when (event) {
            ProfileContract.Event.LoadProfile -> {
                getUserProfile()
                getGroupList()
            }
        }
    }

    private suspend fun getGroupList() {
        getGroupUseCase(id).collect { result ->
            result.onSuccess {
                setState { copy(groupList = it) }
            }.onFail {
                setState { copy(errorMessage = it.message, concrete = State.Concrete.Error) }
            }.onError {
                setState { copy(errorMessage = it.toString(), concrete = State.Concrete.Error) }
            }
        }
    }

    private suspend fun getUserProfile() {
        userProfileUseCase(id).collect { result ->
            result.onSuccess {
                setState { copy(profile = it) }
            }.onFail {
                setState { copy(errorMessage = it.message, concrete = State.Concrete.Error) }
            }.onError {
                setState { copy(errorMessage = it.toString(), concrete = State.Concrete.Error) }
            }
        }
    }
}