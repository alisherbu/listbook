package kaa.alisherbu.listbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kaa.alisherbu.listbook.root.ListbookRoot
import kaa.alisherbu.listbook.root.ListbookRootComponent
import kaa.alisherbu.listbook.root.RootContent
import kaa.alisherbu.listbook.ui.theme.ListbookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val root = listbookRoot(defaultComponentContext())
        setContent {
            ListbookTheme {
                RootContent(root)
            }
        }
    }

    private fun listbookRoot(componentContext: ComponentContext): ListbookRoot =
        ListbookRootComponent(
            componentContext,
            DefaultStoreFactory()
        )
}
