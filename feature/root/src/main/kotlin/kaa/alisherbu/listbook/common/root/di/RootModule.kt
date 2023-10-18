package kaa.alisherbu.listbook.common.root.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.common.root.component.RootComponentImpl
import kaa.alisherbu.listbook.common.root.component.RootComponent

@Module
abstract class RootModule {

    @Binds
    abstract fun componentFactory(impl: RootComponentImpl.Factory): RootComponent.Factory
}