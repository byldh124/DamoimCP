package com.moondroid.damoim.domain.usecase.moim

import com.moondroid.damoim.domain.repository.MoimRepository
import javax.inject.Inject

class GetMoimsUseCase @Inject constructor(private val repository: MoimRepository) {
    suspend operator fun invoke(title: String = "") = repository.getMoims(title)
}