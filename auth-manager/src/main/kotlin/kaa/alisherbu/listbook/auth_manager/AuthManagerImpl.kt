package kaa.alisherbu.listbook.auth_manager

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CompletableDeferred

internal class AuthManagerImpl(private val auth: FirebaseAuth) : AuthManager {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ): Result<User> {
        val completableDeferred = CompletableDeferred<Result<User>>()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = it.user
                completableDeferred.complete(Result.success(User(user?.email.toString())))
            }
            .addOnFailureListener {
                completableDeferred.complete(Result.failure(it))
            }

        return completableDeferred.await()
    }
}
