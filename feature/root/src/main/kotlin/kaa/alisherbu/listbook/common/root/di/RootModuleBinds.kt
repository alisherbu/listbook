package kaa.alisherbu.listbook.common.root.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.common.root.component.RootComponentImpl
import kaa.alisherbu.listbook.common.root.component.RootComponent
import kaa.alisherbu.listbook.common.sign_in.di.SignInModule
import kaa.alisherbu.listbook.common.signup.di.SignupModule

@Module(includes = [SignupModule::class, SignInModule::class])
internal interface RootModuleBinds {

    @Binds
    fun componentFactory(impl: RootComponentImpl.Factory): RootComponent.Factory
}