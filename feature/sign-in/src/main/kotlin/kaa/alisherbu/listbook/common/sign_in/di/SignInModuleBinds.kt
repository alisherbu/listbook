package kaa.alisherbu.listbook.common.sign_in.di


import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.common.sign_in.component.SignInComponent
import kaa.alisherbu.listbook.common.sign_in.component.SignInComponentImpl
import kaa.alisherbu.listbook.common.sign_in.data.repository.SignInRepositoryImpl
import kaa.alisherbu.listbook.common.sign_in.domain.repository.SignInRepository


@Module
internal interface SignInModuleBinds {

    @Binds
    fun componentFactory(impl: SignInComponentImpl.Factory): SignInComponent.Factory

    @Binds
    fun signInRepository(impl: SignInRepositoryImpl): SignInRepository
}