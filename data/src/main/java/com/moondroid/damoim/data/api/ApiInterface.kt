package com.moondroid.damoim.data.api

import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.data.api.URLManager.BLOCK
import com.moondroid.damoim.data.api.URLManager.CREATE_GROUP
import com.moondroid.damoim.data.api.URLManager.CREATE_MOIM
import com.moondroid.damoim.data.api.URLManager.ChECK_APP_VERSION
import com.moondroid.damoim.data.api.URLManager.GET_FAVOR
import com.moondroid.damoim.data.api.URLManager.GET_FAVORITE
import com.moondroid.damoim.data.api.URLManager.GET_GROUP
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
import com.moondroid.damoim.data.model.request.CreateMoimRequest
import com.moondroid.damoim.data.model.request.SaltRequest
import com.moondroid.damoim.data.model.request.SignInRequest
import com.moondroid.damoim.data.model.request.SignUpRequest
import com.moondroid.damoim.data.model.request.SocialSignRequest
import com.moondroid.damoim.data.model.request.UpdateTokenRequest
import com.moondroid.damoim.data.model.response.FavorResponse
import com.moondroid.damoim.data.model.response.GroupListResponse
import com.moondroid.damoim.data.model.response.GroupResponse
import com.moondroid.damoim.data.model.response.MemberResponse
import com.moondroid.damoim.data.model.response.MoimListResponse
import com.moondroid.damoim.data.model.response.MoimResponse
import com.moondroid.damoim.data.model.response.ProfileResponse
import com.moondroid.damoim.data.model.response.SimpleResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiInterface {

    /** 버전 체크 */
    @GET(ChECK_APP_VERSION)
    suspend fun checkAppVersion(
        @Query("packageName") packageName: String,
        @Query("versionCode") versionCode: Int,
        @Query("versionName") versionName: String,
    ): ApiStatus<SimpleResponse>

    // 회원 가입 - 로그인 관련
    // Region Start
    @POST(SIGN_IN)
    suspend fun signIn(@Body body: SignInRequest): ApiStatus<ProfileResponse>

    @POST(SALT)
    suspend fun getSalt(@Body body: SaltRequest): ApiStatus<SimpleResponse>

    @POST(SIGN_UP)
    suspend fun signUp(@Body body: SignUpRequest): ApiStatus<ProfileResponse>

    @POST(SIGN_IN_SOCIAL)
    suspend fun signInSocial(@Body body: SocialSignRequest): ApiStatus<ProfileResponse>
    //Region End

    //유저 정보 Region Start
    @POST(UPDATE_TOKEN)
    suspend fun updateToken(@Body updateTokenRequest: UpdateTokenRequest): ApiStatus<SimpleResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST(UPDATE_PROFILE)
    suspend fun updateProfile(
        @PartMap body: Map<String, RequestBody>,
        @Part file: MultipartBody.Part?,
    ): ApiStatus<ProfileResponse>

    @GET(UPDATE_INTEREST)
    suspend fun updateInterest(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.INTEREST) interest: String,
    ): ApiStatus<SimpleResponse>


    //Region End

    // 그룹 정보 조회 Region Start
    @GET(GET_GROUP)
    suspend fun getAllGroups(): ApiStatus<GroupListResponse>

    @GET(GET_MY_GROUP)
    suspend fun getMyGroups(@Query(RequestParam.ID) userId: String): ApiStatus<GroupListResponse>

    @GET(GET_FAVORITE)
    suspend fun getFavoriteGroups(@Query(RequestParam.ID) id: String): ApiStatus<GroupListResponse>

    @GET(GET_RECENT)
    suspend fun getRecentGroups(@Query(RequestParam.ID) id: String): ApiStatus<GroupListResponse>

    @GET(GET_MEMBER)
    suspend fun getMembers(@Query(RequestParam.TITLE) title: String): ApiStatus<MemberResponse>
    //Region End

    @GET(GET_MOIM)
    suspend fun getMoim(@Query(RequestParam.TITLE) title: String): ApiStatus<MoimListResponse>

    @POST(CREATE_MOIM)
    suspend fun createMoim(@Body body: CreateMoimRequest): ApiStatus<SimpleResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST(CREATE_GROUP)
    suspend fun createGroup(
        @PartMap body: Map<String, RequestBody>,
        @Part file: MultipartBody.Part?,
    ): ApiStatus<GroupResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST(UPDATE_GROUP)
    suspend fun updateGroup(
        @PartMap body: Map<String, RequestBody>,
        @Part thumb: MultipartBody.Part?,
        @Part intro: MultipartBody.Part?,
    ): ApiStatus<GroupResponse>

    @GET(SAVE_RECENT)
    suspend fun saveRecent(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
        @Query(RequestParam.LAST_TIME) lastTime: String,
    ): ApiStatus<SimpleResponse>

    @GET(SAVE_FAVOR)
    suspend fun saveFavor(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
        @Query(RequestParam.ACTIVE) active: Boolean,
    ): ApiStatus<SimpleResponse>

    @GET(JOIN)
    suspend fun join(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
    ): ApiStatus<SimpleResponse>

    @GET(GET_FAVOR)
    suspend fun getFavor(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
    ): ApiStatus<FavorResponse>

    @GET(GET_MOIM_MEMBER)
    suspend fun getMoimMember(
        @Query(RequestParam.JOIN_MEMBER) joinMember: String,
    ): ApiStatus<MemberResponse>

    @GET(JOIN_INTO_MOIM)
    suspend fun joinInMoim(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
        @Query(RequestParam.DATE) date: String,
    ): ApiStatus<MoimResponse>

    @GET(RESIGN)
    suspend fun resign(@Query(RequestParam.ID) id: String): ApiStatus<SimpleResponse>


    @GET(BLOCK)
    suspend fun blockUser(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.BLOCK_ID) blockId: String,
    ): ApiStatus<SimpleResponse>

    @GET(REPORT)
    suspend fun reportUser(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.MESSAGE) msg: String,
    ): ApiStatus<SimpleResponse>
}
