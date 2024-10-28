package com.moondroid.damoim.data.datasource.remote

import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.data.model.dto.GroupItemDTO
import com.moondroid.damoim.data.model.dto.MoimItemDTO
import com.moondroid.damoim.data.model.dto.ProfileDTO
import com.moondroid.damoim.data.model.entity.ProfileEntity
import com.moondroid.damoim.data.model.request.CreateMoimRequest
import com.moondroid.damoim.data.model.request.SaltRequest
import com.moondroid.damoim.data.model.request.SignInRequest
import com.moondroid.damoim.data.model.request.SignUpRequest
import com.moondroid.damoim.data.model.request.SocialSignRequest
import com.moondroid.damoim.domain.model.status.ApiResult
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface RemoteDataSource {
    //Application
    suspend fun checkAppVersion(packageName: String, versionCode: Int, versionName: String): ApiResult<Unit>

    //Sign
    suspend fun getSalt(request: SaltRequest): ApiResult<String>                                        // 로그인 관련
    suspend fun signUp(request: SignUpRequest): ApiResult<ProfileEntity>                                // 회원가입
    suspend fun signIn(request: SignInRequest): ApiResult<ProfileEntity>                                // 로그인
    suspend fun socialSign(request: SocialSignRequest): ApiResult<ProfileEntity>                        // 카카오 로그인
    suspend fun resign(id: String): ApiResult<Unit>

    //Profile
    suspend fun updateToken(
        id: String,
        token: String,
    ): ApiResult<Unit>                                 // 토큰 업데이트

    suspend fun updateInterest(id: String, interest: String): ApiResult<Unit>
    suspend fun updateProfile(
        body: Map<String, RequestBody>,
        thumb: MultipartBody.Part?,
    ): ApiResult<ProfileEntity>

    //Group
    suspend fun getGroupList(id: String, type: GroupType): ApiResult<List<GroupItemDTO>>
    suspend fun createGroup(
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?,
    ): ApiResult<GroupItemDTO>

    suspend fun updateGroup(
        body: Map<String, RequestBody>, thumb: MultipartBody.Part?, intro: MultipartBody.Part?,
    ): ApiResult<GroupItemDTO>

    suspend fun getMembers(title: String): ApiResult<List<ProfileDTO>>
    suspend fun saveRecent(id: String, title: String, lastTime: String): ApiResult<Unit>
    suspend fun joinGroup(id: String, title: String): ApiResult<Unit>
    suspend fun getFavor(id: String, title: String): ApiResult<Boolean>
    suspend fun setFavor(id: String, title: String, active: Boolean): ApiResult<Unit>

    //Moim
    suspend fun createMoim(request: CreateMoimRequest): ApiResult<Unit>
    suspend fun getMoims(title: String): ApiResult<List<MoimItemDTO>>
    suspend fun getMoimMember(joinMembers: String): ApiResult<List<ProfileDTO>>
    suspend fun joinMoim(id: String, title: String, date: String): ApiResult<MoimItemDTO>
}