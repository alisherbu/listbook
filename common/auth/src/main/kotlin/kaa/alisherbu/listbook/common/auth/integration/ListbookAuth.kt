package kaa.alisherbu.listbook.common.auth.integration

interface ListbookAuth {
    fun onSignupClicked()
    fun onSignInClicked()
    sealed class Output {
        object Signup : Output()
    }
}
