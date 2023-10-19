package kaa.alisherbu.listbook.common.signup.data.repository

import com.google.firebase.auth.FirebaseAuth
import kaa.alisherbu.listbook.common.signup.domain.model.User
import kaa.alisherbu.listbook.common.signup.domain.repository.SignupRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class SignupRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : SignupRepository {
    override suspend fun signUp(email: String, password: String): User? {
        return suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val user = result.user
                    if (user != null) continuation.resume(User(user.email.toString()))
                    else continuation.resume(null)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}