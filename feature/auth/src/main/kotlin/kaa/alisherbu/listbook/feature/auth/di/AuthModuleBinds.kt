package kaa.alisherbu.listbook.feature.auth.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.feature.auth.component.AuthComponent
import kaa.alisherbu.listbook.feature.auth.component.AuthComponentImpl

@Module
internal interface AuthModuleBinds {

    @Binds
    fun componentFactory(impl: AuthComponentImpl.Factory): AuthComponent.Factory
}