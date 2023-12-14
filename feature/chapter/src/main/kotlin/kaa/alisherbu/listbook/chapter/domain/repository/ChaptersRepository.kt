package kaa.alisherbu.listbook.chapter.domain.repository

import kaa.alisherbu.listbook.chapter.domain.model.ChapterResponse

interface ChaptersRepository {

    suspend fun getChapterByBookId(bookId: String): List<ChapterResponse>
}
