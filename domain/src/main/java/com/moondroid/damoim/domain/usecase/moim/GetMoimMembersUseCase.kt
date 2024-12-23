package com.moondroid.damoim.domain.usecase.moim

import com.moondroid.damoim.domain.repository.MoimRepository
import javax.inject.Inject

class GetMoimMembersUseCase @Inject constructor(private val repository: MoimRepository) {
    operator fun invoke(joinMembers: String) = repository.getMoimMembers(joinMembers)
}