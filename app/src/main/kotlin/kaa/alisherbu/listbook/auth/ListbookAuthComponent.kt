package kaa.alisherbu.listbook.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kaa.alisherbu.listbook.auth.ListbookAuth.Output
import kaa.alisherbu.listbook.utils.Consumer
import kaa.alisherbu.listbook.utils.invoke

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
