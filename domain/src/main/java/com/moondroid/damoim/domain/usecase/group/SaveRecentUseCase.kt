package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class SaveRecentUseCase @Inject constructor(private val repository: GroupRepository) {
    suspend operator fun invoke(id: String, title: String, lastTime: String) = repository.saveRecent(id, title, lastTime)
}