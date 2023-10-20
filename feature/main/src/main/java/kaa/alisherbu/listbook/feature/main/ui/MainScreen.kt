package kaa.alisherbu.listbook.feature.main.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import kaa.alisherbu.listbook.feature.main.component.MainComponent
import kaa.alisherbu.listbook.feature.main.component.MainComponent.ChildScreen

@Composable
fun MainScreen(component: MainComponent) {
    Scaffold(
        bottomBar = {
            BottomNavigation(modifier = Modifier.fillMaxWidth()) {

            }
        },
        content = { padding ->
            Children(stack = component.screenStack, modifier = Modifier.padding(padding)) {
                when (val child = it.instance) {
                    is ChildScreen.Home -> TODO()
                    is ChildScreen.Profile -> TODO()
                }
            }
        })
}