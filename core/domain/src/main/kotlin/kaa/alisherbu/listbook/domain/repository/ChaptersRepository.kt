package kaa.alisherbu.listbook.domain.repository

import kaa.alisherbu.listbook.domain.model.ChapterResponse
import kotlinx.coroutines.flow.Flow

interface ChaptersRepository {
    fun getChaptersFlow(): Flow<List<ChapterResponse>>
}