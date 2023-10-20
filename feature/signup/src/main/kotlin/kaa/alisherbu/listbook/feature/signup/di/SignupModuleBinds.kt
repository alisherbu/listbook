package kaa.alisherbu.listbook.feature.signup.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.feature.signup.component.SignupComponent
import kaa.alisherbu.listbook.feature.signup.component.SignupComponentImpl
import kaa.alisherbu.listbook.feature.signup.data.repository.SignupRepositoryImpl
import kaa.alisherbu.listbook.feature.signup.domain.repository.SignupRepository
import kaa.alisherbu.listbook.feature.signup.store.SignupStore
import kaa.alisherbu.listbook.feature.signup.store.SignupStoreImpl

@Module
internal interface SignupModuleBinds {

    @Binds
    fun componentFactory(impl: SignupComponentImpl.Factory): SignupComponent.Factory

    @Binds
    fun signupRepository(impl: SignupRepositoryImpl): SignupRepository

    @Binds
    fun signupStore(impl: SignupStoreImpl): SignupStore
}