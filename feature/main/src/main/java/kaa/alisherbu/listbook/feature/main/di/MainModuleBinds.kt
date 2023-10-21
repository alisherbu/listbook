package kaa.alisherbu.listbook.feature.main.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.feature.home.di.HomeModule
import kaa.alisherbu.listbook.feature.main.component.MainComponent
import kaa.alisherbu.listbook.feature.main.component.MainComponentImpl
import kaa.alisherbu.listbook.feature.profile.di.ProfileModule

@Module(includes = [HomeModule::class, ProfileModule::class])
interface MainModuleBinds {

    @Binds
    fun componentFactory(impl: MainComponentImpl.Factory): MainComponent.Factory
}