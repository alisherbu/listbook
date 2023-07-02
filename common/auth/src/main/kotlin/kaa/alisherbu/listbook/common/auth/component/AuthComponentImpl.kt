package kaa.alisherbu.listbook.common.auth.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kaa.alisherbu.listbook.common.auth.component.AuthComponent.Output
import kaa.alisherbu.listbook.common.auth.store.AuthStoreProvider

class AuthComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit,
) : AuthComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        AuthStoreProvider(storeFactory).provide()
    }

    override fun onSignupClicked() {
        output(Output.Signup)
    }

    override fun onSignInClicked() {
    }
}
