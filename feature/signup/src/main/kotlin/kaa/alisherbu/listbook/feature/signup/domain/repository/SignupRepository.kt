package kaa.alisherbu.listbook.feature.signup.domain.repository

import kaa.alisherbu.listbook.feature.signup.domain.model.User

internal interface SignupRepository {
    suspend fun signUp(email: String, password: String): User?
}
