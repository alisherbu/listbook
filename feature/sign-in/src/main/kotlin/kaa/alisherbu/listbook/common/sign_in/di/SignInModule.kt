package kaa.alisherbu.listbook.common.sign_in.di


import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.common.sign_in.component.SignInComponent
import kaa.alisherbu.listbook.common.sign_in.component.SignInComponentImpl


@Module
interface SignInModule {

    @Binds
    fun componentFactory(impl: SignInComponentImpl.Factory): SignInComponent.Factory
}