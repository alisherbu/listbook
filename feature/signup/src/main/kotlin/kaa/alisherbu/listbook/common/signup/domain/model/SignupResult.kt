package kaa.alisherbu.listbook.common.signup.domain.model

import kaa.alisherbu.listbook.auth_manager.User

internal sealed interface SignupResult {
    class Success(val user: User) : SignupResult
    class Error(val message: String) : SignupResult
}