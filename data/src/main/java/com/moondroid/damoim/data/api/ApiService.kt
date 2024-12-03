package com.moondroid.damoim.data.api

import com.moondroid.damoim.common.constant.NoResult
import com.moondroid.damoim.common.constant.RequestParam
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
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiService @Inject constructor(private val client: HttpClient) {
    suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String,
    ): BaseResponse<NoResult> {
        return client.get(URLManager.ChECK_APP_VERSION) {
            url {
                parameters.append("packageName", packageName)
                parameters.append("versionCode", versionCode.toString())
                parameters.append("versionName", versionName)
            }
        }.parseBody()
    }

    suspend fun getAllGroupList(): BaseResponse<List<GroupItemDTO>> {
        return client.get(URLManager.GET_GROUP).parseBody()
    }

    suspend fun getFavoriteGroupList(id: String): BaseResponse<List<GroupItemDTO>> {
        return client.get(URLManager.GET_FAVORITE_GROUP) {
            url {
                parameters.append(RequestParam.ID, id)
            }
        }.parseBody()
    }

    suspend fun getMyGroupList(id: String): BaseResponse<List<GroupItemDTO>> {
        return client.get(URLManager.GET_MY_GROUP) {
            url {
                parameters.append(RequestParam.ID, id)
            }
        }.parseBody()
    }

    suspend fun getRecentGroupList(id: String): BaseResponse<List<GroupItemDTO>> {
        return client.get(URLManager.GET_RECENT_GROUP) {
            url {
                parameters.append(RequestParam.ID, id)
            }
        }.parseBody()
    }

    suspend fun signUp(request: SignUpRequest): BaseResponse<ProfileDTO> {
        return client.post(URLManager.SIGN_UP) {
            setBody(request)
        }.parseBody()
    }

    suspend fun signIn(request: SignInRequest): BaseResponse<ProfileDTO> {
        return client.post(URLManager.SIGN_IN) {
            setBody(request)
        }.parseBody()
    }

    suspend fun getSalt(request: SaltRequest): BaseResponse<String> {
        return client.post(URLManager.SALT) {
            setBody(request)
        }.parseBody()
    }

    suspend fun signInSocial(request: SocialSignRequest): BaseResponse<ProfileDTO> {
        return client.post(URLManager.SIGN_IN_SOCIAL) {
            setBody(request)
        }.parseBody()
    }

    suspend fun resign(id: String): BaseResponse<NoResult> {
        return client.get(URLManager.RESIGN) {
            url {
                parameters.append(RequestParam.ID, id)
            }
        }.parseBody()
    }

    suspend fun updateToken(request: UpdateTokenRequest): BaseResponse<NoResult> {
        return client.post(URLManager.UPDATE_TOKEN) {
            setBody(request)
        }.parseBody()
    }

    suspend fun updateInterest(id: String, interest: String): BaseResponse<NoResult> {
        return client.post(URLManager.UPDATE_INTEREST) {
            url {
                parameters.append(RequestParam.ID, id)
                parameters.append(RequestParam.INTEREST, interest)
            }
        }.parseBody()
    }

    suspend fun updateProfile(part: Map<String, String>, thumb: File?): BaseResponse<ProfileDTO> {
        return client.submitFormWithBinaryData(
            url = URLManager.UPDATE_PROFILE,
            formData {
                part.forEach {
                    append(it.key, it.value)
                }
                if (thumb != null) {
                    append("thumb", thumb.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/*")
                        append(HttpHeaders.ContentDisposition, "filename=\"thumb\"")
                    })
                }
            }
        ).parseBody()
    }

    suspend fun getGroupDetail(title: String): BaseResponse<GroupItemDTO> {
        return client.get(URLManager.GET_GROUP_DETAIL) {
            url {
                parameters.append(RequestParam.TITLE, title)
            }
        }.parseBody()
    }

    suspend fun createGroup(part: Map<String, String>, thumb: File?): BaseResponse<GroupItemDTO> {
        return client.submitFormWithBinaryData(
            url = URLManager.CREATE_GROUP,
            formData {
                part.forEach {
                    append(it.key, it.value)
                }
                if (thumb != null) {
                    append("thumb", thumb.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/*")
                        append(HttpHeaders.ContentDisposition, "filename=\"thumb\"")
                    })
                }
            }
        ).parseBody()
    }

    suspend fun updateGroup(part: Map<String, String>, thumb: File?, intro: File?): BaseResponse<GroupItemDTO> {
        return client.submitFormWithBinaryData(
            url = URLManager.UPDATE_GROUP,
            formData {
                part.forEach {
                    append(it.key, it.value)
                }
                if (thumb != null) {
                    append("thumb", thumb.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/*")
                        append(HttpHeaders.ContentDisposition, "filename=\"thumb\"")
                    })
                }

                if (intro != null) {
                    append("intro", intro.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/*")
                        append(HttpHeaders.ContentDisposition, "filename=\"intro\"")
                    })
                }
            }
        ).parseBody()
    }

    suspend fun getMembers(title: String): BaseResponse<List<ProfileDTO>> {
        return client.get(URLManager.GET_MEMBER) {
            url {
                parameters.append(RequestParam.TITLE, title)
            }
        }.parseBody()
    }

    suspend fun saveRecent(id: String, title: String, lastTime: String): BaseResponse<NoResult> {
        return client.get(URLManager.SAVE_RECENT) {
            url {
                parameters.append(RequestParam.ID, id)
                parameters.append(RequestParam.TITLE, title)
                parameters.append(RequestParam.LAST_TIME, lastTime)
            }
        }.parseBody()
    }

    suspend fun joinGroup(id: String, title: String): BaseResponse<NoResult> {
        return client.get(URLManager.JOIN) {
            url {
                parameters.append(RequestParam.ID, id)
                parameters.append(RequestParam.TITLE, title)
            }
        }.parseBody()
    }

    suspend fun getFavor(id: String, title: String): BaseResponse<Boolean> {
        return client.get(URLManager.GET_FAVOR) {
            url {
                parameters.append(RequestParam.ID, id)
                parameters.append(RequestParam.TITLE, title)
            }
        }.parseBody()
    }

    suspend fun setFavor(id: String, title: String, active: Boolean): BaseResponse<NoResult>  {
        return client.get(URLManager.SAVE_RECENT) {
            url {
                parameters.append(RequestParam.ID, id)
                parameters.append(RequestParam.TITLE, title)
                parameters.append(RequestParam.ACTIVE, active.toString())
            }
        }.parseBody()
    }

    suspend fun createMoim(request: CreateMoimRequest): BaseResponse<NoResult> {
        return client.post(URLManager.CREATE_MOIM) {
            setBody(request)
        }.parseBody()
    }

    suspend fun getMoims(title: String): BaseResponse<List<MoimItemDTO>> {
        return client.get(URLManager.GET_MOIM) {
            url {
                parameters.append(RequestParam.TITLE, title)
            }
        }.parseBody()
    }

    suspend fun getMoimMember(joinMembers: String): BaseResponse<List<ProfileDTO>> {
        return client.get(URLManager.GET_MOIM_MEMBER) {
            url {
                parameters.append(RequestParam.JOIN_MEMBER, joinMembers)
            }
        }.parseBody()
    }

    suspend fun joinMoim(id: String, title: String, date: String): BaseResponse<MoimItemDTO> {
        return client.get(URLManager.JOIN_INTO_MOIM) {
            url {
                parameters.append(RequestParam.ID, id)
                parameters.append(RequestParam.TITLE, title)
                parameters.append(RequestParam.DATE, date)
            }
        }.parseBody()
    }
}