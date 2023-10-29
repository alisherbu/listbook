package kaa.alisherbu.listbook.feature.main.domain.repository

import kaa.alisherbu.listbook.feature.main.domain.model.ChapterResponse

interface ChaptersRepository {
    suspend fun getChapters(bookId: String):List<ChapterResponse>
}