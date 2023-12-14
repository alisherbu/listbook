package kaa.alisherbu.listbook.feature.main.domain.repository

import kaa.alisherbu.listbook.feature.main.domain.model.ChapterResponse
import kotlinx.coroutines.flow.Flow

interface ChaptersRepository {
    suspend fun getChapters(bookId: String): List<ChapterResponse>

    fun getChaptersByBookIdFlow(bookId: String): Flow<List<ChapterResponse>>
}
