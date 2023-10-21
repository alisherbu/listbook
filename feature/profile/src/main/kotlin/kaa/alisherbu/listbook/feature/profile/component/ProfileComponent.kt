package kaa.alisherbu.listbook.feature.profile.component

import com.arkivanov.decompose.ComponentContext

interface ProfileComponent {

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): ProfileComponent
    }
}