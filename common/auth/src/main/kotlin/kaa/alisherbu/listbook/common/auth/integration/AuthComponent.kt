package kaa.alisherbu.listbook.common.auth.integration

interface AuthComponent {
    fun onSignupClicked()
    fun onSignInClicked()
    sealed class Output {
        object Signup : Output()
    }
}
