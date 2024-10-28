package com.moondroid.damoim.domain.usecase.profile

import com.moondroid.damoim.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke() = repository.getProfile()
}