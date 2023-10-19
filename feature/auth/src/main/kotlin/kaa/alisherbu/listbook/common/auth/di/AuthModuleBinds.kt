package kaa.alisherbu.listbook.common.auth.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.common.auth.component.AuthComponent
import kaa.alisherbu.listbook.common.auth.component.AuthComponentImpl

@Module
internal interface AuthModuleBinds {

    @Binds
    fun componentFactory(impl: AuthComponentImpl.Factory): AuthComponent.Factory
}