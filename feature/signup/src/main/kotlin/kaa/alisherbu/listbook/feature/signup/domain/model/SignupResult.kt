package kaa.alisherbu.listbook.feature.signup.domain.model

internal sealed interface SignupResult {
    class Success(val user: User) : SignupResult
    class Error(val message: String) : SignupResult
}
