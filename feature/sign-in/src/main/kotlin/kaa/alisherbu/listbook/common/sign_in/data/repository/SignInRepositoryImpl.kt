package kaa.alisherbu.listbook.common.sign_in.data.repository

import com.google.firebase.auth.FirebaseAuth
import kaa.alisherbu.listbook.common.sign_in.domain.repository.SignInRepository
import javax.inject.Inject

class SignInRepositoryImpl constructor(
    private val firebaseAuth: FirebaseAuth
) : SignInRepository {
    override fun signIn(email: String, password: String) {
        TODO("Not yet implemented")
    }
}