package kaa.alisherbu.listbook.feature.sign_in.data.repository

import com.google.firebase.auth.FirebaseAuth
import kaa.alisherbu.listbook.feature.sign_in.domain.model.User
import kaa.alisherbu.listbook.feature.sign_in.domain.repository.SignInRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class SignInRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : SignInRepository {
    override suspend fun signIn(email: String, password: String): User? {
        return suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val user = result.user
                    if (user != null) {
                        continuation.resume(User(user.email.toString()))
                    } else {
                        continuation.resume(null)
                    }
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}
