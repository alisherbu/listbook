package kaa.alisherbu.listbook.auth

interface ListbookAuth {
    fun onSignupClicked()
    fun onSignInClicked()
    sealed class Output {
        object Signup : Output()
    }
}
