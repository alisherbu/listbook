package kaa.alisherbu.listbook.common.sign_in.domain.repository

interface SignInRepository {
    fun signIn(email: String, password: String)
}