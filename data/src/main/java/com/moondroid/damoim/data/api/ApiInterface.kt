package com.moondroid.damoim.data.api

import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.data.api.URLManager.BLOCK
import com.moondroid.damoim.data.api.URLManager.CREATE_GROUP
import com.moondroid.damoim.data.api.URLManager.CREATE_MOIM
import com.moondroid.damoim.data.api.URLManager.ChECK_APP_VERSION
import com.moondroid.damoim.data.api.URLManager.GET_FAVOR
import com.moondroid.damoim.data.api.URLManager.GET_FAVORITE
import com.moondroid.damoim.data.api.URLManager.GET_GROUP
import com.moondroid.damoim.data.api.URLManager.GET_GROUP_DETAIL
import com.moondroid.damoim.data.api.URLManager.GET_MEMBER
import com.moondroid.damoim.data.api.URLManager.GET_MOIM
import com.moondroid.damoim.data.api.URLManager.GET_MOIM_MEMBER
import com.moondroid.damoim.data.api.URLManager.GET_MY_GROUP
import com.moondroid.damoim.data.api.URLManager.GET_RECENT
import com.moondroid.damoim.data.api.URLManager.JOIN
import com.moondroid.damoim.data.api.URLManager.JOIN_INTO_MOIM
import com.moondroid.damoim.data.api.URLManager.REPORT
import com.moondroid.damoim.data.api.URLManager.RESIGN
import com.moondroid.damoim.data.api.URLManager.SALT
import com.moondroid.damoim.data.api.URLManager.SAVE_FAVOR
import com.moondroid.damoim.data.api.URLManager.SAVE_RECENT
import com.moondroid.damoim.data.api.URLManager.SIGN_IN
import com.moondroid.damoim.data.api.URLManager.SIGN_IN_SOCIAL
import com.moondroid.damoim.data.api.URLManager.SIGN_UP
import com.moondroid.damoim.data.api.URLManager.UPDATE_GROUP
import com.moondroid.damoim.data.api.URLManager.UPDATE_INTEREST
import com.moondroid.damoim.data.api.URLManager.UPDATE_PROFILE
import com.moondroid.damoim.data.api.URLManager.UPDATE_TOKEN
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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface ApiInterface {

    /** 버전 체크 */
    @GET(ChECK_APP_VERSION)
    suspend fun checkAppVersion(
        @Query("packageName") packageName: String,
        @Query("versionCode") versionCode: Int,
        @Query("versionName") versionName: String,
    ): ApiStatus<BaseResponse<Unit>>

    // 회원 가입 - 로그인 관련
    // Region Start
    @POST(SIGN_IN)
    suspend fun signIn(@Body body: SignInRequest): ApiStatus<BaseResponse<ProfileDTO>>

    @POST(SALT)
    suspend fun getSalt(@Body body: SaltRequest): ApiStatus<BaseResponse<String>>

    @POST(SIGN_UP)
    suspend fun signUp(@Body body: SignUpRequest): ApiStatus<BaseResponse<ProfileDTO>>

    @POST(SIGN_IN_SOCIAL)
    suspend fun signInSocial(@Body body: SocialSignRequest): ApiStatus<BaseResponse<ProfileDTO>>
    //Region End

    //유저 정보 Region Start
    @POST(UPDATE_TOKEN)
    suspend fun updateToken(@Body updateTokenRequest: UpdateTokenRequest): ApiStatus<BaseResponse<Unit>>

    @JvmSuppressWildcards
    @Multipart
    @POST(UPDATE_PROFILE)
    suspend fun updateProfile(
        @PartMap body: Map<String, RequestBody>,
        @Part file: MultipartBody.Part?,
    ): ApiStatus<BaseResponse<ProfileDTO>>

    @GET(UPDATE_INTEREST)
    suspend fun updateInterest(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.INTEREST) interest: String,
    ): ApiStatus<BaseResponse<Unit>>


    //Region End

    // 그룹 정보 조회 Region Start
    @GET(GET_GROUP)
    suspend fun getAllGroups(): ApiStatus<BaseResponse<List<GroupItemDTO>>>

    @GET(GET_GROUP_DETAIL)
    suspend fun getGroupDetail(@Query(RequestParam.TITLE) title: String): ApiStatus<BaseResponse<GroupItemDTO>>

    @GET(GET_MY_GROUP)
    suspend fun getMyGroups(@Query(RequestParam.ID) userId: String): ApiStatus<BaseResponse<List<GroupItemDTO>>>

    @GET(GET_FAVORITE)
    suspend fun getFavoriteGroups(@Query(RequestParam.ID) id: String): ApiStatus<BaseResponse<List<GroupItemDTO>>>

    @GET(GET_RECENT)
    suspend fun getRecentGroups(@Query(RequestParam.ID) id: String): ApiStatus<BaseResponse<List<GroupItemDTO>>>

    @GET(GET_MEMBER)
    suspend fun getMembers(@Query(RequestParam.TITLE) title: String): ApiStatus<BaseResponse<List<ProfileDTO>>>
    //Region End

    @GET(GET_MOIM)
    suspend fun getMoim(@Query(RequestParam.TITLE) title: String): ApiStatus<BaseResponse<List<MoimItemDTO>>>

    @POST(CREATE_MOIM)
    suspend fun createMoim(@Body body: CreateMoimRequest): ApiStatus<BaseResponse<Unit>>

    @JvmSuppressWildcards
    @Multipart
    @POST(CREATE_GROUP)
    suspend fun createGroup(
        @PartMap body: Map<String, RequestBody>,
        @Part file: MultipartBody.Part?,
    ): ApiStatus<BaseResponse<GroupItemDTO>>

    @JvmSuppressWildcards
    @Multipart
    @POST(UPDATE_GROUP)
    suspend fun updateGroup(
        @PartMap body: Map<String, RequestBody>,
        @Part thumb: MultipartBody.Part?,
        @Part intro: MultipartBody.Part?,
    ): ApiStatus<BaseResponse<GroupItemDTO>>

    @GET(SAVE_RECENT)
    suspend fun saveRecent(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
        @Query(RequestParam.LAST_TIME) lastTime: String,
    ): ApiStatus<BaseResponse<Unit>>

    @GET(SAVE_FAVOR)
    suspend fun saveFavor(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
        @Query(RequestParam.ACTIVE) active: Boolean,
    ): ApiStatus<BaseResponse<Unit>>

    @GET(JOIN)
    suspend fun join(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
    ): ApiStatus<BaseResponse<Unit>>

    @GET(GET_FAVOR)
    suspend fun getFavor(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
    ): ApiStatus<BaseResponse<Boolean>>

    @GET(GET_MOIM_MEMBER)
    suspend fun getMoimMember(
        @Query(RequestParam.JOIN_MEMBER) joinMember: String,
    ): ApiStatus<BaseResponse<List<ProfileDTO>>>

    @GET(JOIN_INTO_MOIM)
    suspend fun joinInMoim(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
        @Query(RequestParam.DATE) date: String,
    ): ApiStatus<BaseResponse<MoimItemDTO>>

    @GET(RESIGN)
    suspend fun resign(@Query(RequestParam.ID) id: String): ApiStatus<BaseResponse<Unit>>


    @GET(BLOCK)
    suspend fun blockUser(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.BLOCK_ID) blockId: String,
    ): ApiStatus<BaseResponse<Unit>>

    @GET(REPORT)
    suspend fun reportUser(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.MESSAGE) msg: String,
    ): ApiStatus<BaseResponse<Unit>>
}
