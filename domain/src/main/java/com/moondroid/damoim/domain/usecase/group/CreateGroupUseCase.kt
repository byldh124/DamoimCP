package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import java.io.File
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend fun execute(
        id: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        file: File
    ) = createGroup(id, title, location, purpose, interest, file)

    suspend operator fun invoke(
        id: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        file: File
    ) = createGroup(id, title, location, purpose, interest, file)

    private suspend fun createGroup(
        id: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        file: File
    ) = repository.createGroup(id, title, location, purpose, interest, file)
}