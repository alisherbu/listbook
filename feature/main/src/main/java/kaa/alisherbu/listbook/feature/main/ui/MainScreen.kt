package kaa.alisherbu.listbook.feature.main.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import kaa.alisherbu.listbook.feature.home.ui.HomeScreen
import kaa.alisherbu.listbook.feature.main.component.MainComponent
import kaa.alisherbu.listbook.feature.main.component.MainComponent.ChildScreen
import kaa.alisherbu.listbook.feature.profile.ui.ProfileScreen

@Composable
fun MainScreen(component: MainComponent) {
    val screenStack by component.screenStack.subscribeAsState()
    val activeComponent = screenStack.active.instance
    val state by component.state.collectAsState()
    Scaffold(
        bottomBar = {
            Column {
                state.currentChapter?.let {
                    SmallPlayer(
                        isPlaying = state.isPlaying,
                        onPlayOrPause = component::onPlayOrPause,
                        audioBook = it,
                        modifier = Modifier.clickable(
                            onClick = {
                                component.onPlayerClicked(state.currentAudioBook)
                            }
                        ),
                    )
                }

                BottomNavigation(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    BottomNavigationItem(
                        selected = activeComponent is ChildScreen.Home,
                        onClick = component::onHomeClicked,
                        icon = {
                            Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                        },
                        label = {
                            Text(text = "Home")
                        },
                    )
                    BottomNavigationItem(
                        selected = activeComponent is ChildScreen.Profile,
                        onClick = component::onProfileClicked,
                        icon = {
                            Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")
                        },
                        label = {
                            Text(text = "Profile")
                        },
                    )
                }
            }
        },
        modifier = Modifier.navigationBarsPadding(),
        content = {
            MainContent(screenStack = screenStack, modifier = Modifier.padding(it))
        },
    )
}

@Composable
private fun MainContent(
    screenStack: ChildStack<*, ChildScreen>,
    modifier: Modifier = Modifier,
) {
    Children(stack = screenStack, modifier = modifier) {
        when (val child = it.instance) {
            is ChildScreen.Home -> HomeScreen(child.component)
            is ChildScreen.Profile -> ProfileScreen(child.component)
        }
    }
}
