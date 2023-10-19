package kaa.alisherbu.listbook.common.signup.domain.repository

interface SignupRepository {
    fun signUp(email: String, password: String)
}