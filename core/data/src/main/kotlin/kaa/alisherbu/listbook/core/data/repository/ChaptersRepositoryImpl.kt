package kaa.alisherbu.listbook.core.data.repository

import kaa.alisherbu.listbook.core.data.util.toChapterResponse
import kaa.alisherbu.listbook.core.database.dao.ChaptersDao
import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kaa.alisherbu.listbook.domain.model.ChapterResponse
import kaa.alisherbu.listbook.domain.repository.ChaptersRepository
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
