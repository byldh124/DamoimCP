package com.moondroid.damoim.data.model.response

import com.google.gson.annotations.SerializedName
import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.data.model.dto.GroupItemDTO

data class GroupResponse(
    @SerializedName(RequestParam.CODE)
    val code: Int,
    @SerializedName(RequestParam.MESSAGE)
    val message: String? = null,
    @SerializedName(RequestParam.RESULT)
    val result: GroupItemDTO
)