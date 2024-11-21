package com.moondroid.project01_meetingapp.ui.features.group

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.moondroid.damoim.common.constant.ResponseCode
import com.moondroid.damoim.common.util.debug
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.GetFavorUseCase
import com.moondroid.damoim.domain.usecase.group.GetGroupDetailUseCase
import com.moondroid.damoim.domain.usecase.group.GetMembersUseCase
import com.moondroid.damoim.domain.usecase.group.SaveRecentUseCase
import com.moondroid.damoim.domain.usecase.group.SetFavorUseCase
import com.moondroid.damoim.domain.usecase.moim.GetMoimsUseCase
import com.moondroid.damoim.domain.usecase.profile.DeleteProfileUseCase
import com.moondroid.project01_meetingapp.core.base.BaseViewModel
import com.moondroid.project01_meetingapp.core.navigation.GroupRoot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGroupDetailUseCase: GetGroupDetailUseCase,
    private val getFavorUseCase: GetFavorUseCase,
    private val setFavorUseCase: SetFavorUseCase,
    private val saveRecentUseCase: SaveRecentUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val getMembersUseCase: GetMembersUseCase,
    private val getMoimsUseCase: GetMoimsUseCase,
) : BaseViewModel<GroupContract.State, GroupContract.Event, GroupContract.Effect>(GroupContract.State()) {

    val title = mutableStateOf("")

    init {
        title.value = savedStateHandle.toRoute<GroupRoot>().title
        getGroupDetail()
        getMembers()
        getMoims()
        getFavor()
        saveRecent()
    }

    override suspend fun handleEvent(event: GroupContract.Event) {
        when (event) {
            GroupContract.Event.Join -> TODO()
            is GroupContract.Event.UserProfile -> TODO()
            GroupContract.Event.ToggleFavor -> toggleFavor()
        }
    }

    private fun getGroupDetail() {
        viewModelScope.launch {
            getGroupDetailUseCase(title.value).collect { result ->
                result.onSuccess {
                    setState { copy(groupDetail = it) }
                }.onFail {
                    setEffect(GroupContract.Effect.Expired)
                }.onError {

                }
            }
        }
    }

    private fun getMembers() {
        viewModelScope.launch {
            getMembersUseCase(title.value).collect { result ->
                result.onSuccess {
                    setState { copy(members = it) }
                }.onFail {
                    setEffect(GroupContract.Effect.Expired)
                }.onError {

                }
            }
        }
    }

    private fun getMoims() {
        viewModelScope.launch {
            getMoimsUseCase(title.value).collect { result ->
                result.onSuccess {
                    setState { copy(moims = it) }
                }.onFail {
                    setEffect(GroupContract.Effect.Expired)
                }.onError {

                }
            }
        }
    }

    private fun getFavor() {
        viewModelScope.launch {
            getFavorUseCase(title.value).collect { result ->
                result.onSuccess {
                    setState { copy(isFavor = it) }
                }.onError {

                }.onFail {
                    setEffect(GroupContract.Effect.Expired)
                }
            }
        }
    }

    private fun toggleFavor() {
        viewModelScope.launch {
            setFavorUseCase(title.value, !uiState.value.isFavor).collect { result ->
                result.onSuccess {
                    getFavor()
                }
            }
        }
    }

    private fun saveRecent() {
        viewModelScope.launch {
            saveRecentUseCase(title.value, System.currentTimeMillis().toString()).collect { result ->
                result.onSuccess {

                }.onFail {
                    if (it == ResponseCode.PROFILE_ERROR) {
                        setEffect(GroupContract.Effect.Expired)
                    } else {

                    }
                }.onError {

                }
            }
        }
    }
}