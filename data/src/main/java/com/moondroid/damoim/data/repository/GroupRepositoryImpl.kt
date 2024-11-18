package com.moondroid.damoim.data.repository

import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toGroupItem
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.data.model.dao.ProfileDao
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.repository.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDatSource: ProfileDao,
) : GroupRepository {
    override suspend fun getGroupList(type: GroupType): Flow<ApiResult<List<GroupItem>>> = doInFlow {
        val id = runBlocking {
            localDatSource.getProfile()?.id ?: throw IllegalStateException("profile not found")
        }
        emit(remoteDataSource.getGroupList(id, type).convert { it.map { dto -> dto.toGroupItem() } })
    }

    override suspend fun createGroup(
        id: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        file: File,
    ): Flow<ApiResult<GroupItem>> {
        val body = HashMap<String, RequestBody>()
        body[RequestParam.ID] = id.toRequestBody()
        body[RequestParam.TITLE] = title.toRequestBody()
        body[RequestParam.LOCATION] = location.toRequestBody()
        body[RequestParam.PURPOSE] = purpose.toRequestBody()
        body[RequestParam.INTEREST] = interest.toRequestBody()

        val requestBody = file.asRequestBody("image/*".toMediaType())
        val partBody = MultipartBody.Part.createFormData("thumb", file.name, requestBody)

        return doInFlow {
            remoteDataSource.createGroup(body, partBody).convert { it.toGroupItem() }
        }
    }

    override suspend fun updateGroup(
        originTitle: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        information: String,
        thumb: File?,
        intro: File?,
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
        return doInFlow {
            remoteDataSource.updateGroup(body, thumbPart, introPart).convert { it.toGroupItem() }
        }
    }

    override suspend fun getGroupDetail(title: String): Flow<ApiResult<GroupItem>> {
        return doInFlow {
            remoteDataSource.getGroupDetail(title).convert { it.toGroupItem() }
        }
    }

    override suspend fun getMembers(title: String): Flow<ApiResult<List<Profile>>> = doInFlow {
        remoteDataSource.getMembers(title).convert { it.map { dto -> dto.toProfile() } }
    }

    override suspend fun saveRecent(id: String, title: String, lastTime: String): Flow<ApiResult<Unit>> = doInFlow {
        remoteDataSource.saveRecent(id, title, lastTime)
    }

    override suspend fun join(id: String, title: String): Flow<ApiResult<Unit>> = doInFlow {
        remoteDataSource.joinGroup(id, title)
    }

    override suspend fun getFavor(id: String, title: String): Flow<ApiResult<Boolean>> = doInFlow {
        remoteDataSource.getFavor(id, title)
    }

    override suspend fun setFavor(id: String, title: String, active: Boolean): Flow<ApiResult<Unit>> = doInFlow {
        remoteDataSource.setFavor(id, title, active)
    }

    fun <T> doInFlow(scope: suspend FlowCollector<ApiResult<T>>.() -> Unit): Flow<ApiResult<T>> {
        return flow {
            scope(this)
        }.catch {
            emit(ApiResult.Error(it))
        }.flowOn(Dispatchers.IO)
    }
}