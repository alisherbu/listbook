package kaa.alisherbu.listbook.feature.home.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.feature.home.component.HomeComponent
import kaa.alisherbu.listbook.feature.home.store.HomeState

@Composable
fun HomeScreen(component: HomeComponent) {
    val state by component.state.collectAsState()
    Scaffold(
        topBar = {
            HomeTopAppBar()
        },
    ) {
        HomeContent(
            state = state,
            onAudioBookClick = component::onAudioBookClick,
            modifier = Modifier.padding(it),
        )
    }
}

@Composable
private fun HomeContent(
    onAudioBookClick: (AudioBook) -> Unit,
    state: HomeState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(state.audioBooks) {
            AudioBookItem(audioBook = it, onClick = onAudioBookClick)
        }
    }
}
