package com.moondroid.project01_meetingapp.ui.features.sign.signup

import com.moondroid.damoim.domain.usecase.sign.SignUpUseCase
import com.moondroid.project01_meetingapp.core.BaseViewModel
import com.moondroid.project01_meetingapp.ui.features.sign.signup.SignUpContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel<State, Event, Effect>(State.IDLE){
    override suspend fun handleEvent(event: Event) {
        //TODO("Not yet implemented").
    }

}