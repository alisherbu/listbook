package kaa.alisherbu.listbook.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import kaa.alisherbu.listbook.core.data.di.DataModule
import kaa.alisherbu.listbook.core.database.di.DatabaseModule
import kaa.alisherbu.listbook.feature.root.component.RootComponentImpl
import kaa.alisherbu.listbook.feature.root.di.RootModule
import kaa.alisherbu.service.player.di.AudioPlayerModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RootModule::class,
        DatabaseModule::class,
        DataModule::class,
        AudioPlayerModule::class
    ]
)
interface AppComponent {
    val rootComponentFactory: RootComponentImpl.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
