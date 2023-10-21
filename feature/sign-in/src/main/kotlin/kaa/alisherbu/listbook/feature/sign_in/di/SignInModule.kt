package kaa.alisherbu.listbook.feature.sign_in.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.feature.sign_in.domain.usecase.SignInUseCase
import kaa.alisherbu.listbook.feature.sign_in.store.SignInExecutor
import kaa.alisherbu.listbook.feature.sign_in.store.SignInReducer

@Module(includes = [SignInModuleBinds::class])
class SignInModule {
    @Provides
    internal fun provideSignInExecutor(signInUseCase: SignInUseCase): SignInExecutor {
        return SignInExecutor(signInUseCase)
    }
}