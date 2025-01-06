package com.moondroid.damoim.domain.usecase.profile

import com.moondroid.damoim.domain.repository.ProfileRepository
import javax.inject.Inject


class UserProfileUseCase @Inject constructor(private val repository: ProfileRepository){
    operator fun invoke(id: String) = repository.getUserProfile(id)
}