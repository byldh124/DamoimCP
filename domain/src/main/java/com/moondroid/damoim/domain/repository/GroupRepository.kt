package com.moondroid.damoim.domain.repository

import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface GroupRepository {
    suspend fun getGroupList(id: String, type: GroupType): Flow<ApiResult<List<GroupItem>>>

    suspend fun createGroup(
        id: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        file: File
    ): Flow<ApiResult<GroupItem>>

    suspend fun updateGroup(
        originTitle: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        information: String,
        thumb: File?,
        intro: File?
    ): Flow<ApiResult<GroupItem>>

    suspend fun getMembers(title: String): Flow<ApiResult<List<Profile>>>
    suspend fun saveRecent(id: String, title: String, lastTime: String): Flow<ApiResult<Unit>>
    suspend fun join(id: String, title: String): Flow<ApiResult<Unit>>
    suspend fun getFavor(id: String, title: String) : Flow<ApiResult<Boolean>>
    suspend fun setFavor(id: String, title: String, active: Boolean) : Flow<ApiResult<Unit>>
}