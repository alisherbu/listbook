package kaa.alisherbu.listbook.common.auth.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kaa.alisherbu.listbook.common.auth.store.AuthStoreProvider

class AuthComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit,
) : ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        AuthStoreProvider(storeFactory).provide()
    }

    fun onSignupClicked() {
        output(Output.Signup)
    }

    fun onSignInClicked() {
        output(Output.SignIn)
    }

    sealed class Output {
        object Signup : Output()
        object SignIn : Output()
    }
}
