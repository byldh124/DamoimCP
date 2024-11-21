package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class SaveRecentUseCase @Inject constructor(private val repository: GroupRepository) {
    suspend operator fun invoke(title: String, lastTime: String) = repository.saveRecent(title, lastTime)
}