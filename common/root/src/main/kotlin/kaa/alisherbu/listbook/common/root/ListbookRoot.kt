package kaa.alisherbu.listbook.common.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kaa.alisherbu.listbook.common.auth.ListbookAuth

interface ListbookRoot {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Auth(val component: ListbookAuth) : Child()
        data class Home(val text: String) : Child()
        object Signup : Child()
    }
}
