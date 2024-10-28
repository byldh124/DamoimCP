package com.moondroid.damoim.domain.usecase.sign

import com.moondroid.damoim.domain.repository.SignRepository
import javax.inject.Inject

class ResignUseCase @Inject constructor(private val repository: SignRepository) {
    suspend operator fun invoke(id: String) = repository.resign(id)
}