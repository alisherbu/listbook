package kaa.alisherbu.listbook.di

import dagger.Component
import kaa.alisherbu.listbook.common.root.component.DefaultRootComponent
import kaa.alisherbu.listbook.common.root.di.RootModule
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class, RootModule::class])
interface MainComponent {
    val rootComponentFactory: DefaultRootComponent.Factory
}