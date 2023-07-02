package kaa.alisherbu.listbook.common.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kaa.alisherbu.listbook.common.auth.ListbookAuth.Output
import kaa.alisherbu.listbook.core.util.Consumer
import kaa.alisherbu.listbook.core.util.invoke

class ListbookAuthComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: Consumer<Output>,
) : ListbookAuth, ComponentContext by componentContext {
    override fun onSignupClicked() {
        output(Output.Signup)
    }

    override fun onSignInClicked() {
        // no-op
    }
}
