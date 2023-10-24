package kaa.alisherbu.listbook.feature.main.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.feature.home.di.HomeModule
import kaa.alisherbu.listbook.feature.main.component.MainComponent
import kaa.alisherbu.listbook.feature.main.component.MainComponentImpl
import kaa.alisherbu.listbook.feature.main.store.MainStore
import kaa.alisherbu.listbook.feature.main.store.MainStoreImpl
import kaa.alisherbu.listbook.feature.profile.di.ProfileModule

@Module(includes = [HomeModule::class, ProfileModule::class])
internal interface MainModuleBinds {

    @Binds
    fun componentFactory(impl: MainComponentImpl.Factory): MainComponent.Factory

    @Binds
    fun mainStore(impl: MainStoreImpl): MainStore
}