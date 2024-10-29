package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class GetGroupUseCase @Inject constructor(private val repository: GroupRepository) {
    suspend operator fun invoke(id: String, type: GroupType) = repository.getGroupList(id, type)
}