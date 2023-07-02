package kaa.alisherbu.listbook.common.auth.component

interface AuthComponent {
    fun onSignupClicked()
    fun onSignInClicked()
    sealed class Output {
        object Signup : Output()
        object SignIn : Output()
    }
}
