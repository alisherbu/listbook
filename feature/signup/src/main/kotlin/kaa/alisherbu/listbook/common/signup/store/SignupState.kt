package kaa.alisherbu.listbook.common.signup.store

data class SignupState(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val isCreateAccountButtonEnabled: Boolean = false
)
