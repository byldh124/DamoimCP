package com.moondroid.damoim.data.repository

import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toGroupItem
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.repository.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : GroupRepository {
    override suspend fun getGroupList(id: String, type: GroupType): Flow<ApiResult<List<GroupItem>>> =
        flow<ApiResult<List<GroupItem>>> {
            remoteDataSource.getGroupList(id, type).run {
                when (this) {
                    is ApiResult.Success -> emit(ApiResult.Success(response.map { it.toGroupItem() }))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun createGroup(
        id: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        file: File
    ): Flow<ApiResult<GroupItem>> {
        val body = HashMap<String, RequestBody>()
        body[RequestParam.ID] = id.toRequestBody()
        body[RequestParam.TITLE] = title.toRequestBody()
        body[RequestParam.LOCATION] = location.toRequestBody()
        body[RequestParam.PURPOSE] = purpose.toRequestBody()
        body[RequestParam.INTEREST] = interest.toRequestBody()

        val requestBody = file.asRequestBody("image/*".toMediaType())
        val partBody = MultipartBody.Part.createFormData("thumb", file.name, requestBody)

        return flow<ApiResult<GroupItem>> {
            remoteDataSource.createGroup(body, partBody).run {
                when (this) {
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Success -> emit(ApiResult.Success(response.toGroupItem()))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateGroup(
        originTitle: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        information: String,
        thumb: File?,
        intro: File?
    ): Flow<ApiResult<GroupItem>> {
        val body = HashMap<String, RequestBody>()
        body[RequestParam.ORIGIN_TITLE] = originTitle.toRequestBody()
        body[RequestParam.TITLE] = title.toRequestBody()
        body[RequestParam.LOCATION] = location.toRequestBody()
        body[RequestParam.PURPOSE] = purpose.toRequestBody()
        body[RequestParam.INTEREST] = interest.toRequestBody()
        body[RequestParam.INFORMATION] = information.toRequestBody()

        var thumbPart: MultipartBody.Part? = null
        thumb?.let { file ->
            val requestBody = file.asRequestBody("image/*".toMediaType())
            thumbPart = MultipartBody.Part.createFormData("thumb", file.name, requestBody)
        }

        var introPart: MultipartBody.Part? = null
        intro?.let { file ->
            val requestBody = file.asRequestBody("image/*".toMediaType())
            introPart = MultipartBody.Part.createFormData("intro", file.name, requestBody)
        }
        return flow {
            remoteDataSource.updateGroup(body, thumbPart, introPart).run {
                when (this) {
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Success -> emit(ApiResult.Success(response.toGroupItem()))
                }
            }
        }
    }

    override suspend fun getMembers(title: String): Flow<ApiResult<List<Profile>>> =
        flow<ApiResult<List<Profile>>> {
            remoteDataSource.getMembers(title).run {
                when (this) {
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Success -> emit(ApiResult.Success(response.map { it.toProfile() }))
                }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun saveRecent(id: String, title: String, lastTime: String): Flow<ApiResult<Unit>> = flow {
        emit(remoteDataSource.saveRecent(id, title, lastTime))
    }.flowOn(Dispatchers.IO)

    override suspend fun join(id: String, title: String): Flow<ApiResult<Unit>> = flow {
        emit(remoteDataSource.joinGroup(id, title))
    }.flowOn(Dispatchers.IO)

    override suspend fun getFavor(id: String, title: String): Flow<ApiResult<Boolean>> = flow {
        emit(remoteDataSource.getFavor(id, title))
    }.flowOn(Dispatchers.IO)

    override suspend fun setFavor(id: String, title: String, active: Boolean): Flow<ApiResult<Unit>> = flow {
        emit(remoteDataSource.setFavor(id, title, active))
    }.flowOn(Dispatchers.IO)


}