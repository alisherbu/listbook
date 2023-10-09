package kaa.alisherbu.listbook.auth_manager

interface AuthManager {
    suspend fun createUser(email: String, password: String): User?
}
