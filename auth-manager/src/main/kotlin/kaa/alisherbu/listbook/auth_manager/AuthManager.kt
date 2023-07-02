package kaa.alisherbu.listbook.auth_manager

interface AuthManager {
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<User>
}
