package kaa.alisherbu.listbook.common.auth.component

import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.listbook.common.auth.component.AuthComponent.Output

class AuthComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val output: (Output) -> Unit,
) : AuthComponent, ComponentContext by componentContext {

    override fun onSignupClicked() {
        output(Output.Signup)
    }

    override fun onSignInClicked() {
        output(Output.SignIn)
    }


    @AssistedFactory
    interface Factory : AuthComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit
        ): AuthComponentImpl
    }
}
