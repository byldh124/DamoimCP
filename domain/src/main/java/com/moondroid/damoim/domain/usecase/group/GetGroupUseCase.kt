package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.common.constant.GroupType
import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class GetGroupUseCase @Inject constructor(private val repository: GroupRepository) {
    suspend operator fun invoke(type: GroupType) = repository.getGroupList(type)
}