package kaa.alisherbu.listbook.feature.signup.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.feature.signup.domain.usecase.SignUpUseCase
import kaa.alisherbu.listbook.feature.signup.store.SignupReducer
import kaa.alisherbu.listbook.feature.signup.store.SignupExecutor

@Module(includes = [SignupModuleBinds::class])
class SignupModule {

    @Provides
    internal fun provideSignupExecutor(signUpUseCase: SignUpUseCase): SignupExecutor {
        return SignupExecutor(signUpUseCase)
    }
}