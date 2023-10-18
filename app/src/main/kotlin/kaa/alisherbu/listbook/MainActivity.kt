package kaa.alisherbu.listbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kaa.alisherbu.listbook.common.root.ui.RootContent
import kaa.alisherbu.listbook.di.DaggerMainComponent
import kaa.alisherbu.listbook.ui.theme.ListbookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val component = DaggerMainComponent.create().rootComponentFactory(
            defaultComponentContext(),
            LoggingStoreFactory(DefaultStoreFactory())
        )
        setContent {
            ListbookTheme {
                RootContent(component)
            }
        }
    }
}
