package com.moondroid.damoim.domain.usecase.sign

import com.moondroid.damoim.domain.repository.SignRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: SignRepository) {
    operator fun invoke(
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