package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import java.io.File
import javax.inject.Inject

class AddGroupUseCase @Inject constructor(private val repository: GroupRepository) {
    operator fun invoke(
        title: String,
        location: String,
        purpose: String,
        interest: String,
        file: File
    ) = repository.createGroup(title, location, purpose, interest, file)
}