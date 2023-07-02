package kaa.alisherbu.listbook.common.signup

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory

class SignupComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (SignupComponent.Output) -> Unit,
) : SignupComponent {
    override fun onBackClicked() {
        output(SignupComponent.Output.Back)
    }
}
