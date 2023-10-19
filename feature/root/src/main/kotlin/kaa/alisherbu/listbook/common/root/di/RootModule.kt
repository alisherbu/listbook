package kaa.alisherbu.listbook.common.root.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.common.root.component.RootComponentImpl
import kaa.alisherbu.listbook.common.root.component.RootComponent
import kaa.alisherbu.listbook.common.sign_in.di.SignInModule

@Module(includes = [SignInModule::class])
interface RootModule {

    @Binds
    fun componentFactory(impl: RootComponentImpl.Factory): RootComponent.Factory
}