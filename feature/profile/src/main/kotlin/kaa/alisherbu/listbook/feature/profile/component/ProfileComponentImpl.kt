package kaa.alisherbu.listbook.feature.profile.component

import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ProfileComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
) : ProfileComponent, ComponentContext by componentContext {

    @AssistedFactory
    interface Factory : ProfileComponent.Factory {
        override fun invoke(componentContext: ComponentContext): ProfileComponentImpl
    }
}
