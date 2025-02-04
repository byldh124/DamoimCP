package com.moondroid.project01_meetingapp.ui.features.common.splash

import com.moondroid.damoim.common.constant.ResponseCode
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.model.status.onSuccessWithoutResult
import com.moondroid.damoim.domain.usecase.app.CheckVersionUseCase
import com.moondroid.damoim.domain.usecase.profile.GetProfileUseCase
import com.moondroid.project01_meetingapp.core.base.BaseViewModel
import com.moondroid.project01_meetingapp.core.navigation.Home
import com.moondroid.project01_meetingapp.core.navigation.Sign
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val versionUseCase: CheckVersionUseCase,
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel<SplashContract.State, SplashContract.Event, SplashContract.Effect>(SplashContract.State.Idle) {

    override suspend fun handleEvent(event: SplashContract.Event) {
        when (event) {
            SplashContract.Event.CheckAppVersion -> checkAppVersion()
        }
    }

    private suspend fun checkAppVersion() {
        delay(1000)
        versionUseCase("com.moondroid.project01_meetingapp", 33, "3.0.0").collect { result ->
            result.onSuccessWithoutResult {
                checkUser()
            }.onFail {
                when (it.code) {
                    ResponseCode.INACTIVE -> setEffect(SplashContract.Effect.Update)
                    ResponseCode.NOT_EXIST -> setEffect(SplashContract.Effect.NotSupport)
                    else -> setState { SplashContract.State.Fail("서버 오류 : ${it.message}") }
                }
            }.onError {
                setState { SplashContract.State.Fail(it.javaClass.simpleName) }
            }
        }
    }

    private suspend fun checkUser() {
        getProfileUseCase().collect { result ->
            result.onSuccess {
                setEffect(SplashContract.Effect.Navigate(Home))
            }.onFail {
                setEffect(SplashContract.Effect.Navigate(Sign))
            }
        }
    }
}