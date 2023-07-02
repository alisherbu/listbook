package kaa.alisherbu.listbook.auth_manager.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kaa.alisherbu.listbook.auth_manager.AuthManager
import kaa.alisherbu.listbook.auth_manager.AuthManagerImpl
import org.koin.dsl.bind
import org.koin.dsl.module

object Auth {
    val module = module {
        single { AuthManagerImpl(Firebase.auth) } bind AuthManager::class
    }
}
