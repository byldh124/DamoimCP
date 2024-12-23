package com.moondroid.damoim.domain.usecase.sign

import com.moondroid.damoim.domain.repository.SignRepository
import javax.inject.Inject

class SaltUseCase @Inject constructor(private val repository: SignRepository) {
    operator fun invoke(id: String) = repository.getSalt(id)
}