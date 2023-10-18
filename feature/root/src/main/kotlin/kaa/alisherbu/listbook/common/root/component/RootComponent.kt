package kaa.alisherbu.listbook.common.root.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface RootComponent {
    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): RootComponent
    }
}