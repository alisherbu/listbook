package kaa.alisherbu.listbook.chapter.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kaa.alisherbu.listbook.chapter.component.ChapterComponent
import kaa.alisherbu.listbook.chapter.store.ChapterState
import kaa.alisherbu.listbook.core.shared.model.Chapter

@Composable
fun ChapterScreen(component: ChapterComponent) {
    val state by component.state.collectAsState()
    ChapterContent(
        state = state,
        onChapterClick = component::onChapterClicked
    )
}

@Composable
private fun ChapterContent(
    state: ChapterState,
    onChapterClick: (Chapter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(state.chapters) {
            ChapterItem(chapter = it) {
                onChapterClick(it)
            }
        }
    }
}