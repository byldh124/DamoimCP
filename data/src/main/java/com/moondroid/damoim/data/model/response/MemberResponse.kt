package com.moondroid.damoim.data.model.response

import com.google.gson.annotations.SerializedName
import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.data.model.dto.ProfileDTO

data class MemberResponse(
    @SerializedName(RequestParam.CODE)
    val code: Int,
    @SerializedName(RequestParam.MESSAGE)
    val message: String? = null,
    @SerializedName(RequestParam.RESULT)
    val result: List<ProfileDTO>
)
