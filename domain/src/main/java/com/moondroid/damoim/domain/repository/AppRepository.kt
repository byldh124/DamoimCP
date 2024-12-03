package com.moondroid.damoim.domain.repository

import com.moondroid.damoim.common.constant.EmptyResult
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun checkAppVersion(
        packageName: String, versionCode: Int, versionName: String
    ): Flow<ApiResult<EmptyResult>>  // 앱 버전 정보 체크
}