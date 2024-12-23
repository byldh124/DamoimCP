package com.moondroid.damoim.domain.usecase.profile

import com.moondroid.damoim.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateTokenUseCase @Inject constructor(private val repository: ProfileRepository) {
    operator fun invoke(token: String) = repository.updateToken( token)
}