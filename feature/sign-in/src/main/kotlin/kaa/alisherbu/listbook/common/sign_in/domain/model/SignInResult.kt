package kaa.alisherbu.listbook.common.sign_in.domain.model

import kaa.alisherbu.listbook.auth_manager.User

sealed interface SignInResult {
    class Success(val user: User) : SignInResult
    class Error(val message: String) : SignInResult
}