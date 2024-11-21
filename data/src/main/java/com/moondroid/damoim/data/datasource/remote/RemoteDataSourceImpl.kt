package com.moondroid.damoim.data.datasource.remote

import com.moondroid.damoim.common.constant.GroupType
import com.moondroid.damoim.data.api.ApiInterface
import com.moondroid.damoim.data.api.response.ApiStatus
import com.moondroid.damoim.data.model.dto.GroupItemDTO
import com.moondroid.damoim.data.model.dto.MoimItemDTO
import com.moondroid.damoim.data.model.dto.ProfileDTO
import com.moondroid.damoim.data.model.request.CreateMoimRequest
import com.moondroid.damoim.data.model.request.SaltRequest
import com.moondroid.damoim.data.model.request.SignInRequest
import com.moondroid.damoim.data.model.request.SignUpRequest
import com.moondroid.damoim.data.model.request.SocialSignRequest
import com.moondroid.damoim.data.model.request.UpdateTokenRequest
import com.moondroid.damoim.data.model.response.BaseResponse
import com.moondroid.damoim.domain.model.status.ApiResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: ApiInterface) : RemoteDataSource {

    override suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String,
    ): ApiResult<Unit> =
        api.checkAppVersion(packageName, versionCode, versionName).toApiResult()

    override suspend fun signUp(request: SignUpRequest): ApiResult<ProfileDTO> =
        api.signUp(request).toApiResult()

    override suspend fun signIn(request: SignInRequest): ApiResult<ProfileDTO> =
        api.signIn(request).toApiResult()

    override suspend fun getSalt(request: SaltRequest): ApiResult<String> =
        api.getSalt(request).toApiResult()

    override suspend fun socialSign(request: SocialSignRequest): ApiResult<ProfileDTO> =
        api.signInSocial(request).toApiResult()

    override suspend fun resign(id: String): ApiResult<Unit> =
        api.resign(id).toApiResult()

    override suspend fun updateToken(id: String, token: String): ApiResult<Unit> =
        api.updateToken(UpdateTokenRequest(id, token)).toApiResult()


    override suspend fun updateInterest(id: String, interest: String): ApiResult<Unit> =
        api.updateInterest(id, interest).toApiResult()

    override suspend fun updateProfile(
        body: Map<String, RequestBody>,
        thumb: MultipartBody.Part?,
    ): ApiResult<ProfileDTO> =
        api.updateProfile(body, thumb).toApiResult()

    override suspend fun createGroup(
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?,
    ): ApiResult<GroupItemDTO> =
        api.createGroup(body, file).toApiResult()

    override suspend fun updateGroup(
        body: Map<String, RequestBody>,
        thumb: MultipartBody.Part?,
        intro: MultipartBody.Part?,
    ): ApiResult<GroupItemDTO> =
        api.updateGroup(body = body, thumb = thumb, intro = intro).toApiResult()

    override suspend fun getMembers(title: String): ApiResult<List<ProfileDTO>> =
        api.getMembers(title).toApiResult()

    override suspend fun getMoims(title: String): ApiResult<List<MoimItemDTO>> =
        api.getMoim(title).toApiResult()

    override suspend fun saveRecent(id: String, title: String, lastTime: String): ApiResult<Unit> =
        api.saveRecent(id, title, lastTime).toApiResult()

    override suspend fun joinGroup(id: String, title: String): ApiResult<Unit> =
        api.join(id, title).toApiResult()

    override suspend fun getFavor(id: String, title: String): ApiResult<Boolean> =
        api.getFavor(id, title).toApiResult()

    override suspend fun setFavor(id: String, title: String, active: Boolean): ApiResult<Unit> =
        api.saveFavor(id, title, active).toApiResult()

    override suspend fun createMoim(request: CreateMoimRequest): ApiResult<Unit> =
        api.createMoim(request).toApiResult()

    override suspend fun getGroupList(id: String, type: GroupType): ApiResult<List<GroupItemDTO>> {
        val result = when (type) {
            GroupType.ALL -> api.getAllGroups()
            GroupType.FAVORITE -> api.getFavoriteGroups(id)
            GroupType.RECENT -> api.getRecentGroups(id)
            GroupType.MY_GROUP -> api.getMyGroups(id)
        }
        return result.toApiResult()
    }

    override suspend fun getGroupDetail(title: String): ApiResult<GroupItemDTO> =
        api.getGroupDetail(title).toApiResult()

    override suspend fun getMoimMember(joinMembers: String): ApiResult<List<ProfileDTO>> =
        api.getMoimMember(joinMembers).toApiResult()

    override suspend fun joinMoim(id: String, title: String, date: String): ApiResult<MoimItemDTO> =
        api.joinInMoim(id, title, date).toApiResult()

    private fun <T> ApiStatus<BaseResponse<T>>.toApiResult(): ApiResult<T> {
        return when (this) {
            is ApiStatus.Error -> ApiResult.Error(throwable)
            is ApiStatus.Success -> {
                if (response.isSuccess()) ApiResult.Success(response.result)
                else ApiResult.Fail(response.code)
            }
        }
    }
}