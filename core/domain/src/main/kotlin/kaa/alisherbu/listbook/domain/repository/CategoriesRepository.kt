package kaa.alisherbu.listbook.domain.repository

import kaa.alisherbu.listbook.domain.model.CategoriesResponse
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    fun getCategoriesFlow(): Flow<List<CategoriesResponse>>
}
