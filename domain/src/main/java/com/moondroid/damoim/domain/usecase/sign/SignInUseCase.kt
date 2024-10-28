package com.moondroid.damoim.domain.usecase.sign

import com.moondroid.damoim.domain.repository.SignRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: SignRepository) {

    suspend fun signIn(id:String, hashPw: String) = repository.signIn(id, hashPw)
}