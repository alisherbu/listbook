package kaa.alisherbu.listbook

import android.app.Application
import kaa.alisherbu.listbook.auth_manager.di.Auth
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                Auth.module
            )
        }
    }
}
