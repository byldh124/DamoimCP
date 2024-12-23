package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class GetGroupDetailUseCase @Inject constructor(private val groupRepository: GroupRepository) {
    operator fun invoke(title: String) = groupRepository.getGroupDetail(title)
}