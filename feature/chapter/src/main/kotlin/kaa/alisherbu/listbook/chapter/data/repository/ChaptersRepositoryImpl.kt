package kaa.alisherbu.listbook.chapter.data.repository

import kaa.alisherbu.listbook.chapter.data.util.toChapterResponse
import kaa.alisherbu.listbook.chapter.domain.model.ChapterResponse
import kaa.alisherbu.listbook.chapter.domain.repository.ChaptersRepository
import kaa.alisherbu.listbook.core.database.dao.ChaptersDao
import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChaptersRepositoryImpl @Inject constructor(
    private val chaptersDao: ChaptersDao,
    private val dispatchers: AppDispatchers
) : ChaptersRepository {

    override suspend fun getChapterByBookId(bookId: String): List<ChapterResponse> {
        return withContext(dispatchers.io) {
            chaptersDao.getChaptersByBookId(bookId).map(::toChapterResponse)
        }
    }
}
