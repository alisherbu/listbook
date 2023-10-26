package kaa.alisherbu.listbook.feature.sign_in.domain.model

sealed interface SignInResult {
    class Success(val user: User) : SignInResult
    class Error(val message: String) : SignInResult
}
