package com.moondroid.damoim.data.datasource.remote

import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.data.api.ApiInterface
import com.moondroid.damoim.data.api.response.ApiStatus
import com.moondroid.damoim.data.mapper.DataMapper.toProfileEntity
import com.moondroid.damoim.data.model.dto.GroupItemDTO
import com.moondroid.damoim.data.model.dto.MoimItemDTO
import com.moondroid.damoim.data.model.dto.ProfileDTO
import com.moondroid.damoim.data.model.entity.ProfileEntity
import com.moondroid.damoim.data.model.request.CreateMoimRequest
import com.moondroid.damoim.data.model.request.SaltRequest
import com.moondroid.damoim.data.model.request.SignInRequest
import com.moondroid.damoim.data.model.request.SignUpRequest
import com.moondroid.damoim.data.model.request.SocialSignRequest
import com.moondroid.damoim.data.model.request.UpdateTokenRequest
import com.moondroid.damoim.domain.model.status.ApiResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: ApiInterface) : RemoteDataSource {

    override suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String,
    ): ApiResult<Unit> {
        api.checkAppVersion(packageName, versionCode, versionName).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(Unit)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }


    override suspend fun signUp(request: SignUpRequest): ApiResult<ProfileEntity> {
        api.signUp(request).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(response.result.toProfileEntity())
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun signIn(request: SignInRequest): ApiResult<ProfileEntity> {
        api.signIn(request).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(response.result.toProfileEntity())
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun getSalt(request: SaltRequest): ApiResult<String> {
        api.getSalt(request).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(response.result)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }


    override suspend fun socialSign(request: SocialSignRequest): ApiResult<ProfileEntity> {
        api.signInSocial(request).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(response.result.toProfileEntity())
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun resign(id: String): ApiResult<Unit> {
        api.resign(id).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(Unit)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun updateToken(id: String, token: String): ApiResult<Unit> {
        api.updateToken(UpdateTokenRequest(id, token)).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(Unit)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }


    override suspend fun updateInterest(id: String, interest: String): ApiResult<Unit> {
        api.updateInterest(id, interest).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(Unit)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun updateProfile(
        body: Map<String, RequestBody>,
        thumb: MultipartBody.Part?,
    ): ApiResult<ProfileEntity> {
        api.updateProfile(body, thumb).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(response.result.toProfileEntity())
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun createGroup(
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?,
    ): ApiResult<GroupItemDTO> {
        api.createGroup(body, file).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(response.result)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun updateGroup(
        body: Map<String, RequestBody>,
        thumb: MultipartBody.Part?,
        intro: MultipartBody.Part?,
    ): ApiResult<GroupItemDTO> {
        api.updateGroup(body = body, thumb = thumb, intro = intro).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(response.result)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun getMembers(title: String): ApiResult<List<ProfileDTO>> {
        api.getMembers(title).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success ->
                    if (response.code.success()) ApiResult.Success(response.result)
                    else ApiResult.Fail(response.code)
            }
        }
    }

    override suspend fun getMoims(title: String): ApiResult<List<MoimItemDTO>> {
        api.getMoim(title).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success ->
                    if (response.code.success()) ApiResult.Success(response.result)
                    else ApiResult.Fail(response.code)
            }
        }
    }

    override suspend fun saveRecent(id: String, title: String, lastTime: String): ApiResult<Unit> {
        api.saveRecent(id, title, lastTime).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success ->
                    if (response.code.success()) ApiResult.Success(Unit)
                    else ApiResult.Fail(response.code)
            }
        }
    }

    override suspend fun joinGroup(id: String, title: String): ApiResult<Unit> {
        api.join(id, title).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success ->
                    if (response.code.success()) ApiResult.Success(Unit)
                    else ApiResult.Fail(response.code)
            }
        }
    }

    override suspend fun getFavor(id: String, title: String): ApiResult<Boolean> {
        api.getFavor(id, title).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success ->
                    if (response.code.success()) ApiResult.Success(response.result)
                    else ApiResult.Fail(response.code)
            }
        }
    }

    override suspend fun setFavor(id: String, title: String, active: Boolean): ApiResult<Unit> {
        api.saveFavor(id, title, active).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success ->
                    if (response.code.success()) ApiResult.Success(Unit)
                    else ApiResult.Fail(response.code)
            }
        }
    }

    override suspend fun createMoim(request: CreateMoimRequest): ApiResult<Unit> {
        api.createMoim(request).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(Unit)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun getGroupList(id: String, type: GroupType): ApiResult<List<GroupItemDTO>> {
        val result = when (type) {
            GroupType.ALL -> api.getAllGroups()
            GroupType.FAVORITE -> api.getFavoriteGroups(id)
            GroupType.RECENT -> api.getRecentGroups(id)
            GroupType.MY_GROUP -> api.getMyGroups(id)
        }
        return when (result) {
            is ApiStatus.Error -> ApiResult.Error(result.throwable)
            is ApiStatus.Success -> {
                if (result.response.code.success()) ApiResult.Success(result.response.result)
                else ApiResult.Fail(result.response.code)
            }
        }
    }

    override suspend fun getMoimMember(joinMembers: String): ApiResult<List<ProfileDTO>> {
        api.getMoimMember(joinMembers).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(response.result)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun joinMoim(id: String, title: String, date: String): ApiResult<MoimItemDTO> {
        api.joinInMoim(id, title, date).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.code.success()) ApiResult.Success(response.result)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    private fun Int.success() = this == ResponseCode.SUCCESS
}