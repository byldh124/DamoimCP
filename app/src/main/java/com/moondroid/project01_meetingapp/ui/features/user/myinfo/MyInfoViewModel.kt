package com.moondroid.project01_meetingapp.ui.features.user.myinfo

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.profile.GetProfileUseCase
import com.moondroid.damoim.domain.usecase.profile.UpdateProfileUseCase
import com.moondroid.project01_meetingapp.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MyInfoViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
) : BaseViewModel<MyInfoContract.State, MyInfoContract.Event, MyInfoContract.Effect>(
    MyInfoContract.State()
) {
    init {
        viewModelScope.launch {
            getProfileUseCase().collect {
                it.onSuccess { profile ->
                    setState {
                        copy(
                            name = profile.name,
                            birth = profile.birth,
                            gender = profile.gender,
                            location = profile.location,
                            message = profile.message,
                            imageUrl = profile.thumb
                        )
                    }
                }
            }
        }
    }

    override suspend fun handleEvent(event: MyInfoContract.Event) {
        when (event) {
            is MyInfoContract.Event.PutBirth -> setState { copy(birth = event.birth) }
            is MyInfoContract.Event.PutGender -> setState { copy(gender = event.gender) }
            is MyInfoContract.Event.PutLocation -> setState { copy(location = event.location) }
            is MyInfoContract.Event.PutMessage -> setState { copy(message = event.message) }
            is MyInfoContract.Event.PutName -> setState { copy(name = event.name) }
            is MyInfoContract.Event.PutUri -> setState { copy(uri = event.uri) }
            MyInfoContract.Event.Modify -> TODO()
        }
    }

    private suspend fun modify() {
        uiState.value.run {
            val file: File? = uri?.let { File(it.toString()) }
            updateProfileUseCase(name, birth, gender, location, message, file)
        }

    }
}