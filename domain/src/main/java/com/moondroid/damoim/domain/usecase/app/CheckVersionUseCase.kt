package com.moondroid.damoim.domain.usecase.app

import com.moondroid.damoim.domain.repository.AppRepository
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend operator fun invoke(packageName: String, versionCode: Int, versionName: String) =
        appRepository.checkAppVersion(packageName, versionCode, versionName)
}