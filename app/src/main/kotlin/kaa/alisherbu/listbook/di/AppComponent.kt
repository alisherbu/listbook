package kaa.alisherbu.listbook.di

import android.content.Context
import dagger.Component
import kaa.alisherbu.listbook.feature.root.component.RootComponentImpl
import kaa.alisherbu.listbook.feature.root.di.RootModule
import kaa.alisherbu.player.AudioPlayerModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RootModule::class, AudioPlayerModule::class])
interface AppComponent {
    val rootComponentFactory: RootComponentImpl.Factory

    fun applicationContext(applicationContext: Context)
}
