package com.moondroid.damoim.data.repository

import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : AppRepository {
    override suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ): Flow<ApiResult<Unit>> = flow {
        emit(remoteDataSource.checkAppVersion(packageName, versionCode, versionName))
    }.flowOn(Dispatchers.IO)
}