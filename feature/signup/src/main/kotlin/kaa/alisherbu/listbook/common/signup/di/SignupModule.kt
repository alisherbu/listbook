package kaa.alisherbu.listbook.common.signup.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.common.signup.domain.usecase.SignUpUseCase
import kaa.alisherbu.listbook.common.signup.store.SignupReducer
import kaa.alisherbu.listbook.common.signup.store.SignupExecutor

@Module(includes = [SignupModuleBinds::class])
class SignupModule {

    @Provides
    internal fun provideSignupExecutor(signUpUseCase: SignUpUseCase): SignupExecutor {
        return SignupExecutor(signUpUseCase)
    }

    @Provides
    internal fun provideSignupReducer(): SignupReducer {
        return SignupReducer()
    }
}