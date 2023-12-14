package kaa.alisherbu.listbook.domain.repository

import kaa.alisherbu.listbook.domain.model.ChapterResponse

interface ChaptersRepository {

    suspend fun getChapterByBookId(bookId: String): List<ChapterResponse>
}
