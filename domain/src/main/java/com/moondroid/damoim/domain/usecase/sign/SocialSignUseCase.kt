package com.moondroid.damoim.domain.usecase.sign

import com.moondroid.damoim.domain.repository.SignRepository
import javax.inject.Inject

class SocialSignUseCase @Inject constructor(private val repository: SignRepository) {
    operator fun invoke(id: String) = repository.socialSign(id)
}