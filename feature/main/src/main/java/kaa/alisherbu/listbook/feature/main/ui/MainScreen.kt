package kaa.alisherbu.listbook.feature.main.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kaa.alisherbu.listbook.feature.home.ui.HomeScreen
import kaa.alisherbu.listbook.feature.main.component.MainComponent
import kaa.alisherbu.listbook.feature.main.component.MainComponent.ChildScreen
import kaa.alisherbu.listbook.feature.profile.ui.ProfileScreen

@Composable
fun MainScreen(component: MainComponent) {
    val screenStack by component.screenStack.subscribeAsState()
    val activeComponent = screenStack.active.instance
    Scaffold(
        bottomBar = {
            BottomNavigation(modifier = Modifier.fillMaxWidth()) {
                BottomNavigationItem(
                    selected = activeComponent is ChildScreen.Home,
                    onClick = component::onHomeClicked,
                    icon = {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                    }
                )
                BottomNavigationItem(
                    selected = activeComponent is ChildScreen.Profile,
                    onClick = component::onProfileClicked,
                    icon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")
                    }
                )
            }
        },
        content = { padding ->
            Children(stack = screenStack, modifier = Modifier.padding(padding)) {
                when (val child = it.instance) {
                    is ChildScreen.Home -> HomeScreen(child.component)
                    is ChildScreen.Profile -> ProfileScreen(child.component)
                }
            }
        })
}