package kaa.alisherbu.listbook.common.signup.domain.model


internal sealed interface SignupResult {
    class Success(val user: User) : SignupResult
    class Error(val message: String) : SignupResult
}