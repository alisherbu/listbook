package kaa.alisherbu.listbook.common.sign_in.di


import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.common.sign_in.component.SignInComponent
import kaa.alisherbu.listbook.common.sign_in.component.SignInComponentImpl
import kaa.alisherbu.listbook.common.sign_in.data.repository.SignInRepositoryImpl
import kaa.alisherbu.listbook.common.sign_in.domain.repository.SignInRepository
import kaa.alisherbu.listbook.common.sign_in.store.SignInStore
import kaa.alisherbu.listbook.common.sign_in.store.SignInStoreImpl


@Module
internal interface SignInModuleBinds {

    @Binds
    fun componentFactory(impl: SignInComponentImpl.Factory): SignInComponent.Factory

    @Binds
    fun signInRepository(impl: SignInRepositoryImpl): SignInRepository

    @Binds
    fun signInStore(impl: SignInStoreImpl): SignInStore
}