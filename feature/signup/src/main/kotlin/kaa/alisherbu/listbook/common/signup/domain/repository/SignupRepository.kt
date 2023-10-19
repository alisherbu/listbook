package kaa.alisherbu.listbook.common.signup.domain.repository

import kaa.alisherbu.listbook.common.signup.domain.model.User


internal interface SignupRepository {
    suspend fun signUp(email: String, password: String): User?
}