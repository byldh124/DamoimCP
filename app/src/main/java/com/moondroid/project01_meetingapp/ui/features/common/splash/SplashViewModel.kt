package com.moondroid.project01_meetingapp.ui.features.common.splash

import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.app.CheckVersionUseCase
import com.moondroid.damoim.domain.usecase.profile.ProfileUseCase
import com.moondroid.project01_meetingapp.core.BaseViewModel
import com.moondroid.project01_meetingapp.core.Sign
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val versionUseCase: CheckVersionUseCase,
    private val profileUseCase: ProfileUseCase,
) : BaseViewModel<SplashContract.State, SplashContract.Event, SplashContract.Effect>(SplashContract.State.Idle) {

    override suspend fun handleEvent(event: SplashContract.Event) {
        when (event) {
            SplashContract.Event.CheckAppVersion -> checkAppVersion()
        }
    }

    private suspend fun checkAppVersion() {
        delay(1000)
        versionUseCase("com.moondroid.project01_meetingapp", 33, "3.0.0").collect { result ->
            result.onSuccess {
                checkUser()
            }.onFail {
                when (it) {
                    ResponseCode.INACTIVE -> setEffect(SplashContract.Effect.Update)
                    ResponseCode.NOT_EXIST -> setEffect(SplashContract.Effect.NotSupport)
                    else -> setState(SplashContract.State.Fail("서버 오류 : $it"))
                }
            }.onError {
                setState(SplashContract.State.Fail(it.javaClass.simpleName))
            }
        }
    }

    private suspend fun checkUser() {
        profileUseCase().collect { result ->
            result.onSuccess {
                setEffect(SplashContract.Effect.Navigate(Sign))
            }.onError {
                setEffect(SplashContract.Effect.Navigate(Sign))
            }
        }
    }
}