package kaa.alisherbu.listbook.chapter.domain.repository

import kaa.alisherbu.listbook.chapter.domain.model.ChapterResponse
import kotlinx.coroutines.flow.Flow

interface ChaptersRepository {

    suspend fun getChapterByBookId(bookId: String): List<ChapterResponse>
}