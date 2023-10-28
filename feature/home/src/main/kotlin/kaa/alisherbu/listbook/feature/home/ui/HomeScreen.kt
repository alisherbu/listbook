package kaa.alisherbu.listbook.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.feature.home.component.HomeComponent
import kaa.alisherbu.listbook.feature.home.store.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
            onRefresh = component::onRefresh,
            modifier = Modifier.padding(it),
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeContent(
    onAudioBookClick: (AudioBook) -> Unit,
    onRefresh: () -> Unit,
    state: HomeState,
    modifier: Modifier = Modifier,
) {
    val refreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing, onRefresh = onRefresh
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    items(10) {
                        BookItem()
                    }
                }
            }
            items(state.audioBooks) {
                AudioBookItem(audioBook = it, onClick = onAudioBookClick)
            }
        }
        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(HomeComponentPreview())
}

private class HomeComponentPreview : HomeComponent {
    val homeState = HomeState(
        audioBooks = listOf(
            AudioBook(id = "1", name = "Chapter 1", audioUrl = "", isDownloaded = false),
            AudioBook(id = "2", name = "Chapter 2", audioUrl = "", isDownloaded = false),
            AudioBook(id = "3", name = "Chapter 3", audioUrl = "", isDownloaded = false)
        )
    )
    override val state: StateFlow<HomeState> = MutableStateFlow(homeState)

    override fun onAudioBookClick(audioBook: AudioBook) = Unit

    override fun onRefresh() = Unit

}
