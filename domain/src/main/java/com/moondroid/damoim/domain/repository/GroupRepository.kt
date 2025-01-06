package com.moondroid.damoim.domain.repository

import com.moondroid.damoim.common.constant.GroupType
import com.moondroid.damoim.common.constant.NoResult
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface GroupRepository {
    fun getUserGroupList(type: GroupType): Flow<ApiResult<List<GroupItem>>>

    fun getUserGroupList(id: String): Flow<ApiResult<List<GroupItem>>>

    fun createGroup(
        title: String,
        location: String,
        purpose: String,
        interest: String,
        thumb: File
    ): Flow<ApiResult<GroupItem>>

    fun updateGroup(
        originTitle: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        information: String,
        thumb: File?,
        intro: File?
    ): Flow<ApiResult<GroupItem>>

    fun getGroupDetail(title: String): Flow<ApiResult<GroupItem>>

    fun getMembers(title: String): Flow<ApiResult<List<Profile>>>
    fun saveRecent(title: String, lastTime: String): Flow<ApiResult<NoResult>>
    fun join(title: String): Flow<ApiResult<NoResult>>
    fun getFavor(title: String): Flow<ApiResult<Boolean>>
    fun setFavor(title: String, active: Boolean): Flow<ApiResult<NoResult>>
}