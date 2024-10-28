package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class JoinGroupUseCase @Inject constructor(private val repository: GroupRepository){
    suspend fun execute(id: String, title: String) = repository.join(id, title)
    suspend operator fun invoke(id: String, title: String) = repository.join(id, title)
    private suspend fun join(id: String, title: String) = repository.join(id, title)
}