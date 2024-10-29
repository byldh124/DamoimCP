package com.moondroid.damoim.domain.usecase.moim

import com.moondroid.damoim.domain.repository.GroupRepository
import com.moondroid.damoim.domain.repository.MoimRepository
import javax.inject.Inject

class CreateMoimUseCase @Inject constructor(private val repository: MoimRepository) {
    suspend operator fun invoke(
        title: String,
        address: String,
        date: String,
        time: String,
        pay: String,
        lat: Double,
        lng: Double,
        joinMember: String
    ) = repository.createMoim(title, address, date, time, pay, lat, lng, joinMember)
}