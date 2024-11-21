package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import java.io.File
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend fun execute(
        title: String,
        location: String,
        purpose: String,
        interest: String,
        file: File
    ) = createGroup(title, location, purpose, interest, file)

    suspend operator fun invoke(
        title: String,
        location: String,
        purpose: String,
        interest: String,
        file: File
    ) = createGroup(title, location, purpose, interest, file)

    private suspend fun createGroup(
        title: String,
        location: String,
        purpose: String,
        interest: String,
        file: File
    ) = repository.createGroup(title, location, purpose, interest, file)
}