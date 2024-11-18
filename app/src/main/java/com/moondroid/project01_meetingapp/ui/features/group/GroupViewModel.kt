package com.moondroid.project01_meetingapp.ui.features.group

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.GetMembersUseCase
import com.moondroid.damoim.domain.usecase.moim.GetMoimsUseCase
import com.moondroid.project01_meetingapp.core.base.BaseViewModel
import com.moondroid.project01_meetingapp.core.navigation.GroupRoot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMembersUseCase: GetMembersUseCase,
    private val getMoimsUseCase: GetMoimsUseCase,
) : BaseViewModel<GroupContract.State, GroupContract.Event, GroupContract.Effect>(GroupContract.State()) {

    val title = mutableStateOf("")

    init {
        title.value = savedStateHandle.toRoute<GroupRoot>().title
        getMembers()
        getMoims()
    }

    override suspend fun handleEvent(event: GroupContract.Event) {
    }

    private fun getMembers() {
        viewModelScope.launch {
            getMembersUseCase(title.value).collect { result ->
                result.onSuccess {
                    setState { copy(members = it) }
                }.onFail {

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

                }.onError {

                }
            }
        }
    }
}