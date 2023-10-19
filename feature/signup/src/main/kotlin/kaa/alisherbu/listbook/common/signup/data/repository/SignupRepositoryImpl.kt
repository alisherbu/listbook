package kaa.alisherbu.listbook.common.signup.data.repository

import com.google.firebase.auth.FirebaseAuth
import kaa.alisherbu.listbook.common.signup.domain.repository.SignupRepository

internal class SignupRepositoryImpl(private val firebaseAuth: FirebaseAuth) : SignupRepository {
    override fun signUp(email: String, password: String) {

    }
}