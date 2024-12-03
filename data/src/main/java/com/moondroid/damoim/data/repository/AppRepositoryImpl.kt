package com.moondroid.damoim.data.repository

import com.moondroid.damoim.common.constant.EmptyResult
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.model.status.doInFlow
import com.moondroid.damoim.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : AppRepository {
    override suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String,
    ): Flow<ApiResult<EmptyResult>> = doInFlow {
        emit(remoteDataSource.checkAppVersion(packageName, versionCode, versionName))
    }
}