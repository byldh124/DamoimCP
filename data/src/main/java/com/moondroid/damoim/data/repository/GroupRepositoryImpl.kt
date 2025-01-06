package com.moondroid.damoim.data.repository

import com.moondroid.damoim.common.constant.GroupType
import com.moondroid.damoim.common.constant.NoResult
import com.moondroid.damoim.common.constant.RequestParam
import com.moondroid.damoim.data.datasource.local.LocalDataSource
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toGroupItem
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.model.status.doInFlow
import com.moondroid.damoim.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDatSource: LocalDataSource,
) : GroupRepository {
    override fun getUserGroupList(type: GroupType): Flow<ApiResult<List<GroupItem>>> = doInFlow {
        val id = localDatSource.getId()
        emit(remoteDataSource.getGroupList(id, type).convert { it.map { dto -> dto.toGroupItem() } })
    }

    override fun getUserGroupList(id: String): Flow<ApiResult<List<GroupItem>>> {
        return doInFlow {
            emit(remoteDataSource.getGroupList(id, GroupType.MY_GROUP).convert { it.map { dto -> dto.toGroupItem() } })
        }
    }

    override fun createGroup(
        title: String,
        location: String,
        purpose: String,
        interest: String,
        thumb: File,
    ): Flow<ApiResult<GroupItem>> {
        val id = localDatSource.getId()
        val body = HashMap<String, String>()
        body[RequestParam.ID] = id
        body[RequestParam.TITLE] = title
        body[RequestParam.LOCATION] = location
        body[RequestParam.PURPOSE] = purpose
        body[RequestParam.INTEREST] = interest

        return doInFlow {
            emit(remoteDataSource.createGroup(body, thumb).convert { it.toGroupItem() })
        }
    }

    override fun updateGroup(
        originTitle: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        information: String,
        thumb: File?,
        intro: File?,
    ): Flow<ApiResult<GroupItem>> {
        val body = HashMap<String, String>()
        body[RequestParam.ORIGIN_TITLE] = originTitle
        body[RequestParam.TITLE] = title
        body[RequestParam.LOCATION] = location
        body[RequestParam.PURPOSE] = purpose
        body[RequestParam.INTEREST] = interest
        body[RequestParam.INFORMATION] = information

        return doInFlow {
            emit(remoteDataSource.updateGroup(body, thumb, intro).convert { it.toGroupItem() })
        }
    }

    override fun getGroupDetail(title: String): Flow<ApiResult<GroupItem>> {
        return doInFlow {
            emit(remoteDataSource.getGroupDetail(title).convert { it.toGroupItem() })
        }
    }

    override fun getMembers(title: String): Flow<ApiResult<List<Profile>>> = doInFlow {
        emit(remoteDataSource.getMembers(title).convert { it.map { dto -> dto.toProfile() } })
    }

    override fun saveRecent(title: String, lastTime: String): Flow<ApiResult<NoResult>> = doInFlow {
        val id = localDatSource.getId()
        emit(remoteDataSource.saveRecent(id, title, lastTime))
    }

    override fun join(title: String): Flow<ApiResult<NoResult>> = doInFlow {
        val id = localDatSource.getId()
        emit(remoteDataSource.joinGroup(id, title))
    }

    override fun getFavor(title: String): Flow<ApiResult<Boolean>> = doInFlow {
        val id = localDatSource.getId()
        emit(remoteDataSource.getFavor(id, title))
    }

    override fun setFavor(title: String, active: Boolean): Flow<ApiResult<NoResult>> = doInFlow {
        val id = localDatSource.getId()
        emit(remoteDataSource.setFavor(id, title, active))
    }
}