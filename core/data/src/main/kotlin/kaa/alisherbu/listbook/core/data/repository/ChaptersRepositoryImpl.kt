package kaa.alisherbu.listbook.core.data.repository

import kaa.alisherbu.listbook.core.data.util.toChapterResponseList
import kaa.alisherbu.listbook.core.database.dao.ChaptersDao
import kaa.alisherbu.listbook.domain.model.ChapterResponse
import kaa.alisherbu.listbook.domain.repository.ChaptersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChaptersRepositoryImpl @Inject constructor(
    private val chaptersDao: ChaptersDao
) : ChaptersRepository {
    override fun getChaptersFlow(): Flow<List<ChapterResponse>> {
        return chaptersDao.getAudioBooks().map(::toChapterResponseList)
    }
}