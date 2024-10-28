package com.moondroid.damoim.domain.usecase.sign

import com.moondroid.damoim.domain.repository.SignRepository
import javax.inject.Inject

class SocialSignUseCase @Inject constructor(private val repository: SignRepository) {

    suspend fun execute(id: String) = socialSign(id)

    suspend operator fun invoke(id: String) = socialSign(id)

    private suspend fun socialSign(id: String) = repository.socialSign(id)
}