package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import java.io.File
import javax.inject.Inject

class UpdateGroupUseCase @Inject constructor(private val repository: GroupRepository) {
    suspend operator fun invoke(
        originTitle: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        information: String,
        thumb: File?,
        intro: File?,
    ) = repository.updateGroup(originTitle, title, location, purpose, interest, information, thumb, intro)
}
