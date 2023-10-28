package kaa.alisherbu.listbook.chapter.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kaa.alisherbu.listbook.chapter.component.ChapterComponent

@Composable
fun ChapterScreen(component: ChapterComponent) {
    Text(text = component.toString())
}