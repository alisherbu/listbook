package kaa.alisherbu.listbook.common.root.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.common.root.component.DefaultRootComponent
import kaa.alisherbu.listbook.common.root.component.RootComponent

@Module
abstract class RootModule {

    @Binds
    abstract fun componentFactory(impl: DefaultRootComponent.Factory): RootComponent.Factory
}