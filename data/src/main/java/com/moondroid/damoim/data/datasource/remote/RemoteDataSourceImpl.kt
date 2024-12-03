package com.moondroid.damoim.data.datasource.remote

import com.moondroid.damoim.common.constant.GroupType
import com.moondroid.damoim.common.constant.NoResult
import com.moondroid.damoim.data.api.ApiService
import com.moondroid.damoim.data.model.dto.GroupItemDTO
import com.moondroid.damoim.data.model.dto.MoimItemDTO
import com.moondroid.damoim.data.model.dto.ProfileDTO
import com.moondroid.damoim.data.model.request.CreateMoimRequest
import com.moondroid.damoim.data.model.request.SaltRequest
import com.moondroid.damoim.data.model.request.SignInRequest
import com.moondroid.damoim.data.model.request.SignUpRequest
import com.moondroid.damoim.data.model.request.SocialSignRequest
import com.moondroid.damoim.data.model.request.UpdateTokenRequest
import com.moondroid.damoim.data.model.response.toApiResult
import com.moondroid.damoim.domain.model.status.ApiResult
import java.io.File
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: ApiService) :
    RemoteDataSource {

    override suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String,
    ): ApiResult<NoResult> {
        return api.checkAppVersion(packageName, versionCode, versionName).toApiResult()
    }


    override suspend fun signUp(request: SignUpRequest): ApiResult<ProfileDTO> =
        api.signUp(request).toApiResult()

    override suspend fun signIn(request: SignInRequest): ApiResult<ProfileDTO> =
        api.signIn(request).toApiResult()

    override suspend fun getSalt(request: SaltRequest): ApiResult<String> =
        api.getSalt(request).toApiResult()

    override suspend fun socialSign(request: SocialSignRequest): ApiResult<ProfileDTO> =
        api.signInSocial(request).toApiResult()

    override suspend fun resign(id: String): ApiResult<NoResult> =
        api.resign(id).toApiResult()

    override suspend fun updateToken(request: UpdateTokenRequest): ApiResult<NoResult> =
        api.updateToken(request).toApiResult()


    override suspend fun updateInterest(id: String, interest: String): ApiResult<NoResult> =
        api.updateInterest(id, interest).toApiResult()

    override suspend fun updateProfile(
        body: Map<String, String>,
        thumb: File?,
    ): ApiResult<ProfileDTO> = api.updateProfile(body, thumb).toApiResult()

    override suspend fun getGroupList(id: String, type: GroupType): ApiResult<List<GroupItemDTO>> {
        val result = when (type) {
            GroupType.ALL -> api.getAllGroupList()
            GroupType.FAVORITE -> api.getFavoriteGroupList(id)
            GroupType.RECENT -> api.getRecentGroupList(id)
            GroupType.MY_GROUP -> api.getMyGroupList(id)
        }
        return result.toApiResult()
    }

    override suspend fun getGroupDetail(title: String): ApiResult<GroupItemDTO> {
        return api.getGroupDetail(title).toApiResult()
    }

    override suspend fun createGroup(body: Map<String, String>, thumb: File?): ApiResult<GroupItemDTO> {
        return api.createGroup(body, thumb).toApiResult()
    }

    override suspend fun updateGroup(body: Map<String, String>, thumb: File?, intro: File?): ApiResult<GroupItemDTO> {
        return api.updateGroup(body, thumb, intro).toApiResult()
    }

    override suspend fun getMembers(title: String): ApiResult<List<ProfileDTO>> {
        return api.getMembers(title).toApiResult()
    }

    override suspend fun saveRecent(id: String, title: String, lastTime: String): ApiResult<NoResult> {
        return api.saveRecent(id, title, lastTime).toApiResult()
    }

    override suspend fun joinGroup(id: String, title: String): ApiResult<NoResult> {
        return api.joinGroup(id, title).toApiResult()
    }

    override suspend fun getFavor(id: String, title: String): ApiResult<Boolean> {
        return api.getFavor(id, title).toApiResult()
    }

    override suspend fun setFavor(id: String, title: String, active: Boolean): ApiResult<NoResult> {
        return api.setFavor(id, title, active).toApiResult()
    }

    override suspend fun createMoim(request: CreateMoimRequest): ApiResult<NoResult> {
        return api.createMoim(request).toApiResult()
    }

    override suspend fun getMoims(title: String): ApiResult<List<MoimItemDTO>> {
        return api.getMoims(title).toApiResult()
    }

    override suspend fun getMoimMember(joinMembers: String): ApiResult<List<ProfileDTO>> {
        return api.getMoimMember(joinMembers).toApiResult()
    }

    override suspend fun joinMoim(id: String, title: String, date: String): ApiResult<MoimItemDTO> {
        return api.joinMoim(id, title, date).toApiResult()
    }
}