package com.moondroid.project01_meetingapp.ui.features.group.add

import com.moondroid.damoim.common.util.simpleName
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.AddGroupUseCase
import com.moondroid.project01_meetingapp.core.base.BaseViewModel
import com.moondroid.project01_meetingapp.ui.features.group.add.GroupAddContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class GroupAddViewModel @Inject constructor(
    private val addGroupUseCase: AddGroupUseCase,
) : BaseViewModel<State, Event, Effect>(State()) {

    override suspend fun handleEvent(event: Event) {
        when (event) {
            is Event.PutTitle -> setState {copy(title = event.title)}
            is Event.PutInterest -> setState { copy(interest = event.interest) }
            is Event.PutLocation -> setState { copy(location = event.location) }
            is Event.PutPurpose -> setState { copy(purpose = event.purpose) }
            is Event.PutUri -> setState { copy(uri = event.uri) }
            Event.RESET -> setState { copy(concrete = State.Concrete.Idle, errorMessage = "") }
            is Event.Add -> addGroup(event.path)
        }
    }

    private suspend fun addGroup(path: String) {
        val title = uiState.value.title
        val location = uiState.value.location
        val purpose = uiState.value.purpose
        val interest = uiState.value.interest
        val file = File(path)
        setState { copy(concrete = State.Concrete.Loading) }
        addGroupUseCase(title, location, purpose, interest, file).collect { result ->
            result.onSuccess {
                setEffect(Effect.NavigateToGroup(it.title))
            }.onError {
                setState { copy(concrete = State.Concrete.Error, errorMessage = it.simpleName()) }
            }.onFail {
                setState { copy(concrete = State.Concrete.Error, errorMessage = it.message) }
            }
        }
    }


}