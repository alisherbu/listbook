package kaa.alisherbu.listbook.common.signup.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.common.signup.component.SignupComponent
import kaa.alisherbu.listbook.common.signup.component.SignupComponentImpl
import kaa.alisherbu.listbook.common.signup.data.repository.SignupRepositoryImpl
import kaa.alisherbu.listbook.common.signup.domain.repository.SignupRepository
import kaa.alisherbu.listbook.common.signup.store.SignupStore
import kaa.alisherbu.listbook.common.signup.store.SignupStoreImpl

@Module
internal interface SignupModuleBinds {

    @Binds
    fun componentFactory(impl: SignupComponentImpl.Factory): SignupComponent.Factory

    @Binds
    fun signupRepository(impl: SignupRepositoryImpl): SignupRepository

    @Binds
    fun signupStore(impl: SignupStoreImpl): SignupStore
}