package kaa.alisherbu.listbook.common.sign_in.domain.repository

import kaa.alisherbu.listbook.auth_manager.User

interface SignInRepository {
    suspend fun signIn(email: String, password: String): User?
}