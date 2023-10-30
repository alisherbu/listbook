package kaa.alisherbu.listbook.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        contentWindowInsets = WindowInsets(0.dp)
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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row {
                    Text(text = "New Releases Book")
                }
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.audioBooks) {
                        BookItem(
                            audioBook = it,
                            onAudioBookClick = onAudioBookClick
                        )
                    }
                }
            }
            item {
                Row {
                    Text(text = "Category")
                }
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(6) {
                        Box(
                            modifier = Modifier
                                .height(50.dp)
                                .width(100.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.LightGray)
                        ) {
                            Text(
                                text = "Category $it",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }

            item {
                Row {
                    Text(text = "Featured Books")
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    state.audioBooks.forEach {
                        BookDetailItem(
                            audioBook = it,
                            onAudioBookClick = onAudioBookClick
                        )
                    }
                }
            }

        }
        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

