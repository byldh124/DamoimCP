package com.moondroid.damoim.domain.usecase.profile

import com.moondroid.damoim.domain.repository.ProfileRepository
import javax.inject.Inject

class InterestUseCase @Inject constructor(private val repository: ProfileRepository) {
    operator fun invoke(interest: String) = repository.updateInterest(interest)
}