package kaa.alisherbu.listbook.common.auth

interface ListbookAuth {
    fun onSignupClicked()
    fun onSignInClicked()
    sealed class Output {
        object Signup : Output()
    }
}
