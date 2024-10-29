package com.moondroid.damoim.domain.usecase.sign

import com.moondroid.damoim.domain.repository.SignRepository
import javax.inject.Inject

class SaltUseCase @Inject constructor(private val repository: SignRepository) {
    suspend fun execute(id: String) = getSalt(id)

    suspend operator fun invoke(id: String) = getSalt(id)

    private suspend fun getSalt(id: String) = repository.getSalt(id)
}