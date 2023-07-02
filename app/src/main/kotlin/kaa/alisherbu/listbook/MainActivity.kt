package kaa.alisherbu.listbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kaa.alisherbu.listbook.common.root.RootComponent
import kaa.alisherbu.listbook.common.root.RootComponentImpl
import kaa.alisherbu.listbook.common.root.RootContent
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

    private fun listbookRoot(componentContext: ComponentContext): RootComponent =
        RootComponentImpl(
            componentContext,
            DefaultStoreFactory()
        )
}
