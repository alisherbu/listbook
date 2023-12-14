package kaa.alisherbu.listbook.chapter.store

import kaa.alisherbu.listbook.core.shared.model.Chapter

data class ChapterState(
    val chapters: List<Chapter> = emptyList(),
)
