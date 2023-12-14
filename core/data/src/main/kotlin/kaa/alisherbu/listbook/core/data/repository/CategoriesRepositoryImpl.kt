package kaa.alisherbu.listbook.core.data.repository

import kaa.alisherbu.listbook.core.database.dao.CategoriesDao
import kaa.alisherbu.listbook.core.database.entity.CategoryEntity
import kaa.alisherbu.listbook.domain.model.CategoriesResponse
import kaa.alisherbu.listbook.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesDao: CategoriesDao
) : CategoriesRepository {
    override fun getCategoriesFlow(): Flow<List<CategoriesResponse>> {
        return categoriesDao.getCategoriesFlow().map(::toCategoryResponseList)
    }

    private fun toCategoryResponseList(categories: List<CategoryEntity>): List<CategoriesResponse> {
        return categories.map(::toCategoryResponse)
    }

    private fun toCategoryResponse(entity: CategoryEntity): CategoriesResponse {
        return CategoriesResponse(
            id = entity.id,
            name = entity.name
        )
    }
}
