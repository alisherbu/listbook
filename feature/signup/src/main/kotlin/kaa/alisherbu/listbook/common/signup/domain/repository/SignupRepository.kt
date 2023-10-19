package kaa.alisherbu.listbook.common.signup.domain.repository

import kaa.alisherbu.listbook.auth_manager.User

interface SignupRepository {
    suspend fun signUp(email: String, password: String): User?
}