package com.moondroid.damoim.data.datasource.remote

import com.moondroid.damoim.common.constant.GroupType
import com.moondroid.damoim.common.constant.NoResult
import com.moondroid.damoim.data.model.dto.GroupItemDTO
import com.moondroid.damoim.data.model.dto.MoimItemDTO
import com.moondroid.damoim.data.model.dto.ProfileDTO
import com.moondroid.damoim.data.model.request.CreateMoimRequest
import com.moondroid.damoim.data.model.request.SaltRequest
import com.moondroid.damoim.data.model.request.SignInRequest
import com.moondroid.damoim.data.model.request.SignUpRequest
import com.moondroid.damoim.data.model.request.SocialSignRequest
import com.moondroid.damoim.data.model.request.UpdateTokenRequest
import com.moondroid.damoim.domain.model.status.ApiResult
import java.io.File

interface RemoteDataSource {
    //Application
    suspend fun checkAppVersion(packageName: String, versionCode: Int, versionName: String): ApiResult<NoResult>

    //Sign
    suspend fun getSalt(request: SaltRequest): ApiResult<String>                                        // 로그인 관련
    suspend fun signUp(request: SignUpRequest): ApiResult<ProfileDTO>                                // 회원가입
    suspend fun signIn(request: SignInRequest): ApiResult<ProfileDTO>                                // 로그인
    suspend fun socialSign(request: SocialSignRequest): ApiResult<ProfileDTO>                        // 카카오 로그인
    suspend fun resign(id: String): ApiResult<NoResult>

    //Profile
    suspend fun updateToken(request: UpdateTokenRequest): ApiResult<NoResult>                                 // 토큰 업데이트

    suspend fun updateInterest(id: String, interest: String): ApiResult<NoResult>
    suspend fun updateProfile(
        body: Map<String, String>,
        thumb: File?,
    ): ApiResult<ProfileDTO>
    suspend fun getUserProfile(id: String): ApiResult<ProfileDTO>

    //Group
    suspend fun getGroupList(id: String, type: GroupType): ApiResult<List<GroupItemDTO>>
    suspend fun getGroupDetail(title: String): ApiResult<GroupItemDTO>
    suspend fun createGroup(
        body: Map<String, String>,
        thumb: File?,
    ): ApiResult<GroupItemDTO>

    suspend fun updateGroup(
        body: Map<String, String>, thumb: File?, intro: File?,
    ): ApiResult<GroupItemDTO>

    suspend fun getMembers(title: String): ApiResult<List<ProfileDTO>>
    suspend fun saveRecent(id: String, title: String, lastTime: String): ApiResult<NoResult>
    suspend fun joinGroup(id: String, title: String): ApiResult<NoResult>
    suspend fun getFavor(id: String, title: String): ApiResult<Boolean>
    suspend fun setFavor(id: String, title: String, active: Boolean): ApiResult<NoResult>

    //Moim
    suspend fun createMoim(request: CreateMoimRequest): ApiResult<NoResult>
    suspend fun getMoims(title: String): ApiResult<List<MoimItemDTO>>
    suspend fun getMoimMember(joinMembers: String): ApiResult<List<ProfileDTO>>
    suspend fun joinMoim(id: String, title: String, date: String): ApiResult<MoimItemDTO>
}