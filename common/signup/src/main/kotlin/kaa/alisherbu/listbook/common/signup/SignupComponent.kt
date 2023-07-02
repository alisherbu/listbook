package kaa.alisherbu.listbook.common.signup

interface SignupComponent {

    fun onBackClicked()

    sealed class Output {
        object Back : Output()
    }
}
