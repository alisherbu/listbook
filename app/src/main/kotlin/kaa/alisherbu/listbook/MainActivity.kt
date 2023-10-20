package kaa.alisherbu.listbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import kaa.alisherbu.listbook.di.DaggerAppComponent
import kaa.alisherbu.listbook.feature.root.ui.RootContent
import kaa.alisherbu.listbook.ui.theme.ListbookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val component = DaggerAppComponent.create().rootComponentFactory(defaultComponentContext())
        setContent {
            ListbookTheme {
                RootContent(component)
            }
        }
    }
}
