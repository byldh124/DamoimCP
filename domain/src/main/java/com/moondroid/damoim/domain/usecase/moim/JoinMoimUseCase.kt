package com.moondroid.damoim.domain.usecase.moim

import com.moondroid.damoim.domain.repository.MoimRepository
import javax.inject.Inject


class JoinMoimUseCase @Inject constructor(private val repository: MoimRepository) {
    suspend operator fun invoke(id: String, title: String, date: String) = repository.joinMoim(id, title, date)
}