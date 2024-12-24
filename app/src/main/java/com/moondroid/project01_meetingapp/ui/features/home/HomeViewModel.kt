package com.moondroid.project01_meetingapp.ui.features.home

import com.moondroid.damoim.common.constant.GroupType
import com.moondroid.damoim.common.constant.ResponseCode
import com.moondroid.damoim.common.util.simpleName
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.GetGroupUseCase
import com.moondroid.damoim.domain.usecase.profile.GetProfileUseCase
import com.moondroid.damoim.domain.usecase.profile.UpdateProfileUseCase
import com.moondroid.project01_meetingapp.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getGroupUseCase: GetGroupUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : BaseViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>(HomeContract.State()) {

    override suspend fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.Fetch -> getGroup(event.groupType)
            HomeContract.Event.GetProfile -> getProfile()
        }
    }

    private suspend fun getProfile() {
        getProfileUseCase().collect { result ->
            result.onSuccess {
                setState { copy(profile = it) }
            }.onError {
                setState {
                    copy(
                        errorMessage = "에러 : $it",
                        concrete = HomeContract.State.Concrete.Error,
                        retryType = HomeContract.Event.GetProfile
                    )
                }
            }
        }
    }


    private suspend fun getGroup(groupType: GroupType) {
        getGroupUseCase(groupType).collect { result ->
            result.onSuccess {
                setState { copy(list = it, concrete = HomeContract.State.Concrete.Idle) }
            }.onError {
                setState {
                    copy(
                        errorMessage = it.simpleName(),
                        concrete = HomeContract.State.Concrete.Error,
                        retryType = HomeContract.Event.Fetch(groupType)
                    )
                }
            }.onFail {
                when (it.code) {
                    ResponseCode.PROFILE_ERROR -> {
                        setEffect(HomeContract.Effect.Expired)
                    }

                    else -> setState {
                        copy(
                            errorMessage = "서버 에러 : ${it.message}",
                            concrete = HomeContract.State.Concrete.Error,
                            retryType = HomeContract.Event.Fetch(groupType)
                        )
                    }
                }
            }
        }
    }
}