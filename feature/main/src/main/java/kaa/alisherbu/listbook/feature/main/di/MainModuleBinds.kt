package kaa.alisherbu.listbook.feature.main.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.feature.main.component.MainComponent
import kaa.alisherbu.listbook.feature.main.component.MainComponentImpl

@Module
interface MainModuleBinds {

    @Binds
    fun componentFactory(impl: MainComponentImpl.Factory): MainComponent.Factory
}