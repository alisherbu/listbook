package kaa.alisherbu.listbook

import android.app.Application
import com.google.firebase.FirebaseApp
import kaa.alisherbu.listbook.auth_manager.di.Auth
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
        startKoin {
            modules(
                Auth.module
            )
        }
    }
}
