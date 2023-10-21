package kaa.alisherbu.listbook.feature.profile.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.feature.profile.component.ProfileComponent
import kaa.alisherbu.listbook.feature.profile.component.ProfileComponentImpl

@Module
internal interface ProfileModuleBinds {

    @Binds
    fun componentFactory(impl: ProfileComponentImpl.Factory): ProfileComponent.Factory
}