package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class GetFavorUseCase @Inject constructor(private val repository: GroupRepository){
    operator fun invoke(title: String) = repository.getFavor(title)
}