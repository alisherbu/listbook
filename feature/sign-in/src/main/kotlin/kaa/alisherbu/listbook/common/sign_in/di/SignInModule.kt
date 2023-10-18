package kaa.alisherbu.listbook.common.sign_in.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.common.sign_in.data.repository.SignInRepositoryImpl
import kaa.alisherbu.listbook.common.sign_in.domain.repository.SignInRepository

@Module
abstract class SignInModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Binds
    abstract fun bindSignInRepository(repository: SignInRepositoryImpl): SignInRepository
}