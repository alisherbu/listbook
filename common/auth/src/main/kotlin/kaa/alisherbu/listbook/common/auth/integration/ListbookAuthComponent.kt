package kaa.alisherbu.listbook.common.auth.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kaa.alisherbu.listbook.common.auth.integration.ListbookAuth.Output
import kaa.alisherbu.listbook.common.auth.store.AuthStoreProvider
import kaa.alisherbu.listbook.core.util.Consumer
import kaa.alisherbu.listbook.core.util.invoke

class ListbookAuthComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: Consumer<Output>,
) : ListbookAuth, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        AuthStoreProvider(storeFactory).provide()
    }

    override fun onSignupClicked() {
        output(Output.Signup)
    }

    override fun onSignInClicked() {
    }
}
