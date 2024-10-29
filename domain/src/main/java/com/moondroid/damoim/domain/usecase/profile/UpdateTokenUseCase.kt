package com.moondroid.damoim.domain.usecase.profile

import com.moondroid.damoim.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateTokenUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend fun execute(id: String, token: String) = updateToken(id, token)
    suspend operator fun invoke(id: String, token: String) = updateToken(id, token)
    private suspend fun updateToken(id: String, token: String) = repository.updateToken(id, token)
}