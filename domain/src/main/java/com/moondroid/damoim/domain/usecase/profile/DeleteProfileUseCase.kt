package com.moondroid.damoim.domain.usecase.profile

import com.moondroid.damoim.domain.repository.ProfileRepository
import javax.inject.Inject

data class DeleteProfileUseCase @Inject constructor(private val repository: ProfileRepository) {
    operator fun invoke() = repository.deleteProfile()
}
