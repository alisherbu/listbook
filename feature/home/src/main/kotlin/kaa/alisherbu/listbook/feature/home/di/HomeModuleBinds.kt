package kaa.alisherbu.listbook.feature.home.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.feature.home.component.HomeComponent
import kaa.alisherbu.listbook.feature.home.component.HomeComponentImpl

@Module
internal interface HomeModuleBinds {

    @Binds
    fun componentFactory(impl: HomeComponentImpl.Factory): HomeComponent.Factory
}