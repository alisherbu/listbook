package kaa.alisherbu.listbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import kaa.alisherbu.listbook.di.AppModule
import kaa.alisherbu.listbook.di.DaggerAppComponent
import kaa.alisherbu.listbook.feature.root.ui.RootScreen
import kaa.alisherbu.listbook.ui.theme.ListbookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(applicationContext))
            .build()
        val rootComponent = appComponent.rootComponentFactory(defaultComponentContext())
        setContent {
            ListbookTheme {
                RootScreen(rootComponent)
            }
        }
    }
}
