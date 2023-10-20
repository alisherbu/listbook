package kaa.alisherbu.listbook.di

import dagger.Component
import kaa.alisherbu.listbook.feature.root.component.RootComponentImpl
import kaa.alisherbu.listbook.feature.root.di.RootModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RootModule::class])
interface AppComponent {
    val rootComponentFactory: RootComponentImpl.Factory
}