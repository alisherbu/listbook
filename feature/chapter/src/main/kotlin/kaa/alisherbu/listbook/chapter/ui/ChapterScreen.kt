package kaa.alisherbu.listbook.chapter.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    LazyColumn(modifier = modifier) {
        items(state.chapters) {
            ChapterItem(audioBook = it, onClick = onChapterClick)
        }
    }
}