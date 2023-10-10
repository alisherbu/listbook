package kaa.alisherbu.listbook.auth_manager

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class AuthManagerImpl(private val auth: FirebaseAuth) : AuthManager {
    override suspend fun createUser(
        email: String,
        password: String,
    ): User? {
        return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
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

    override suspend fun signInUser(email: String, password: String): User? {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { result ->
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
