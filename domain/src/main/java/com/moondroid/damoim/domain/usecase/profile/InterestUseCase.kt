package com.moondroid.damoim.domain.usecase.profile

import com.moondroid.damoim.domain.repository.ProfileRepository
import javax.inject.Inject

class InterestUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend fun execute(interest: String) = update(interest)
    suspend operator fun invoke(interest: String) = update(interest)
    private suspend fun update(interest: String) = repository.updateInterest(interest)
}