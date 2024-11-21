package com.moondroid.damoim.domain.usecase.profile

import com.moondroid.damoim.domain.repository.ProfileRepository
import java.io.File
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(
        name: String,
        birth: String,
        gender: String,
        location: String,
        message: String,
        thumb: File?,
    ) = repository.updateProfile(name, birth, gender, location, message, thumb)
}