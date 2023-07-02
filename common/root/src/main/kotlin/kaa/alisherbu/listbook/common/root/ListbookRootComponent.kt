package kaa.alisherbu.listbook.common.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kaa.alisherbu.listbook.common.auth.ListbookAuth
import kaa.alisherbu.listbook.common.auth.ListbookAuthComponent
import kaa.alisherbu.listbook.common.root.ListbookRoot.Child
import kaa.alisherbu.listbook.core.util.Consumer
import kotlinx.parcelize.Parcelize

class ListbookRootComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val listbookAuth: (ComponentContext, Consumer<ListbookAuth.Output>) -> ListbookAuth,
) : ListbookRoot, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
    ) : this(
        componentContext,
        storeFactory,
        listbookAuth = { childContext, output ->
            ListbookAuthComponent(childContext, storeFactory, output)
        }
    )

    private val navigation = StackNavigation<Configuration>()

    override val childStack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        initialConfiguration = Configuration.Auth,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext,
    ): Child = when (configuration) {
        is Configuration.Auth -> {
            Child.Auth(listbookAuth(componentContext, Consumer(::onAuthOutput)))
        }

        is Configuration.Home -> Child.Home("")
        Configuration.Signup -> Child.Signup
    }

    private fun onAuthOutput(output: ListbookAuth.Output) {
        when (output) {
            ListbookAuth.Output.Signup -> navigation.push(Configuration.Signup)
        }
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Auth : Configuration()

        @Parcelize
        data class Home(val text: String) : Configuration()

        @Parcelize
        object Signup : Configuration()
    }
}
