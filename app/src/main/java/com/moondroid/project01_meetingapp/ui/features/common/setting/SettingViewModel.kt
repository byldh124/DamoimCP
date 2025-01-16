package com.moondroid.project01_meetingapp.ui.features.common.setting

import com.moondroid.damoim.common.util.debug
import com.moondroid.damoim.common.util.simpleName
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.model.status.onSuccessWithoutResult
import com.moondroid.damoim.domain.usecase.profile.DeleteProfileUseCase
import com.moondroid.damoim.domain.usecase.sign.ResignUseCase
import com.moondroid.project01_meetingapp.core.base.BaseViewModel
import com.moondroid.project01_meetingapp.ui.features.common.setting.SettingContract.Effect
import com.moondroid.project01_meetingapp.ui.features.common.setting.SettingContract.Event
import com.moondroid.project01_meetingapp.ui.features.common.setting.SettingContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val resignUseCase: ResignUseCase,
) : BaseViewModel<State, Event, Effect>(State.Idle) {

    override suspend fun handleEvent(event: Event) {
        when (event) {
            Event.Logout -> logout()
            Event.Resign -> resign()
            Event.Retry -> setState { State.Idle }
        }
    }

    private suspend fun logout() {
        setState { State.Loading }
        deleteProfileUseCase().collect { result ->
            debug("result : $result")
            result.onSuccess {
                setState { State.Idle }
                setEffect(Effect.NavigateToSign)
            }.onError {
                setState { State.Error(it.simpleName()) }
            }
        }
    }

    private suspend fun resign() {
        resignUseCase().collect { result ->
            result.onSuccessWithoutResult {
                setState { State.Idle }
                setEffect(Effect.NavigateToSign)
            }.onFail {
                setState { State.Error(it.message) }
            }.onError {
                setState { State.Error(it.simpleName()) }
            }
        }
    }
}