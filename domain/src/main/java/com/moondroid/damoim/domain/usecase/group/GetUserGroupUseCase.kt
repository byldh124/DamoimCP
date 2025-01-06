package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.common.constant.GroupType
import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class GetUserGroupUseCase @Inject constructor(private val repository: GroupRepository) {
    operator fun invoke(type: GroupType) = repository.getUserGroupList(type)
    operator fun invoke(id: String) = repository. getUserGroupList(id)
}