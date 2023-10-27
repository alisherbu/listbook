package kaa.alisherbu.listbook.di

import android.content.Context
import dagger.Component
import kaa.alisherbu.listbook.core.data.di.DataModule
import kaa.alisherbu.listbook.core.database.di.DatabaseModule
import kaa.alisherbu.listbook.feature.root.component.RootComponentImpl
import kaa.alisherbu.listbook.feature.root.di.RootModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RootModule::class,
        DatabaseModule::class,
        DataModule::class,
    ]
)
interface AppComponent {
    val rootComponentFactory: RootComponentImpl.Factory

    fun applicationContext(applicationContext: Context)
}
