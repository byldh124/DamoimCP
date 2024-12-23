package com.moondroid.damoim.domain.usecase.moim

import com.moondroid.damoim.domain.repository.MoimRepository
import javax.inject.Inject


class JoinMoimUseCase @Inject constructor(private val repository: MoimRepository) {
    operator fun invoke(title: String, date: String) = repository.joinMoim(title, date)
}