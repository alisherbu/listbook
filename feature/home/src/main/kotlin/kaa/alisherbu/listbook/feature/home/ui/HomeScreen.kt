package kaa.alisherbu.listbook.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
