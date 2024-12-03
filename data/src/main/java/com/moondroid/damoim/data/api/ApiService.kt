package com.moondroid.damoim.data.api

import com.moondroid.damoim.common.constant.EmptyResult
import com.moondroid.damoim.data.api.ktor.work
import com.moondroid.damoim.data.model.dto.GroupItemDTO
import com.moondroid.damoim.data.model.response.BaseResponse
import io.ktor.client.HttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiService @Inject constructor(private val client: HttpClient) {
    suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String,
    ): BaseResponse<EmptyResult> {
        return client.work<BaseResponse<EmptyResult>>(URLManager.ChECK_APP_VERSION) {
            url {
                parameters.append("packageName", packageName)
                parameters.append("versionCode", versionCode.toString())
                parameters.append("versionName", versionName)
            }
        }
    }

    suspend fun getGroupList(): BaseResponse<List<GroupItemDTO>> {
        return client.work<BaseResponse<List<GroupItemDTO>>>(URLManager.GET_GROUP)
    }
}