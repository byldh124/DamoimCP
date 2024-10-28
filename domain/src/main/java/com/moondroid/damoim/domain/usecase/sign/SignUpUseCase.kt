package com.moondroid.damoim.domain.usecase.sign

import com.moondroid.damoim.domain.repository.SignRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: SignRepository) {

    suspend fun execute(
        id: String,
        hashPw: String,
        salt: String,
        name: String,
        birth: String,
        gender: String,
        location: String,
        interest: String,
        thumb: String
    ) = signUp(id, hashPw, salt, name, birth, gender, location, interest, thumb)

    suspend operator fun invoke(
        id: String,
        hashPw: String,
        salt: String,
        name: String,
        birth: String,
        gender: String,
        location: String,
        interest: String,
        thumb: String
    ) = signUp(id, hashPw, salt, name, birth, gender, location, interest, thumb)

    private suspend fun signUp(
        id: String,
        hashPw: String,
        salt: String,
        name: String,
        birth: String,
        gender: String,
        location: String,
        interest: String,
        thumb: String
    ) = repository.signUp(id, hashPw, salt, name, birth, gender, location, interest, thumb)
}