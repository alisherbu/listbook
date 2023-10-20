package kaa.alisherbu.listbook.feature.root.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.feature.auth.di.AuthModule
import kaa.alisherbu.listbook.feature.root.component.RootComponentImpl
import kaa.alisherbu.listbook.feature.root.component.RootComponent
import kaa.alisherbu.listbook.feature.sign_in.di.SignInModule
import kaa.alisherbu.listbook.feature.signup.di.SignupModule

@Module(includes = [AuthModule::class, SignupModule::class, SignInModule::class])
internal interface RootModuleBinds {

    @Binds
    fun componentFactory(impl: RootComponentImpl.Factory): RootComponent.Factory
}