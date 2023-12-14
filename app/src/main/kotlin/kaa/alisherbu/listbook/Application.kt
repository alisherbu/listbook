package kaa.alisherbu.listbook

import android.app.Application
import com.google.firebase.FirebaseApp
import timber.log.Timber
import timber.log.Timber.DebugTree

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        FirebaseApp.initializeApp(applicationContext)
    }
}
