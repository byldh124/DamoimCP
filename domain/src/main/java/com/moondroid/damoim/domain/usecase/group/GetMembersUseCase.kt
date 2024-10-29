package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class GetMembersUseCase @Inject constructor(private val repository: GroupRepository) {
    suspend fun execute(title: String) = getMembers(title)

    suspend operator fun invoke(title: String) = getMembers(title)

    private suspend fun getMembers(title: String) = repository.getMembers(title)
}