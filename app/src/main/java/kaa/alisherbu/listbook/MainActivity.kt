package kaa.alisherbu.listbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import kaa.alisherbu.listbook.navigator.ProvideComponentContext
import kaa.alisherbu.listbook.screens.MainContent
import kaa.alisherbu.listbook.ui.theme.ListbookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val rootComponentContext = defaultComponentContext()
        setContent {
            ListbookTheme {
                ProvideComponentContext(rootComponentContext) {
                    MainContent()
                }
            }
        }
    }
}
