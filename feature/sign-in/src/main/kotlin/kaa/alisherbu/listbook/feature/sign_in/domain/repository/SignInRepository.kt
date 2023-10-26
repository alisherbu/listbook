package kaa.alisherbu.listbook.feature.sign_in.domain.repository

import kaa.alisherbu.listbook.feature.sign_in.domain.model.User

internal interface SignInRepository {
    suspend fun signIn(email: String, password: String): User?
}
