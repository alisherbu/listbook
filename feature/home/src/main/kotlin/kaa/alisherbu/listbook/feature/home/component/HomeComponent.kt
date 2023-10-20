package kaa.alisherbu.listbook.feature.home.component

import com.arkivanov.decompose.ComponentContext

interface HomeComponent {

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): HomeComponent
    }
}