package kaa.alisherbu.listbook.feature.main.data.repository

import kaa.alisherbu.listbook.core.database.dao.ChaptersDao
import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kaa.alisherbu.listbook.feature.main.data.util.toChapterResponse
import kaa.alisherbu.listbook.feature.main.domain.model.ChapterResponse
import kaa.alisherbu.listbook.feature.main.domain.repository.ChaptersRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject


internal class ChaptersRepositoryImpl @Inject constructor(
    private val chaptersDao: ChaptersDao,
    private val dispatchers: AppDispatchers
) : ChaptersRepository {
    override suspend fun getChapters(bookId: String): List<ChapterResponse> {
        return withContext(dispatchers.io) {
            chaptersDao.getChaptersByBookId(bookId).map(::toChapterResponse)
        }
    }
}