package kaa.alisherbu.listbook.domain.usecase

import kaa.alisherbu.listbook.core.shared.model.Category
import kaa.alisherbu.listbook.domain.model.CategoriesResponse
import kaa.alisherbu.listbook.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {
    operator fun invoke(): Flow<List<Category>> {
        return categoriesRepository.getCategoriesFlow().map(::toCategoryList)
    }

    private fun toCategoryList(categories: List<CategoriesResponse>): List<Category> {
        return categories.map(::toCategory)
    }

    private fun toCategory(response: CategoriesResponse): Category {
        return Category(
            id = response.id.toString(),
            name = response.name.toString(),
            isSelected = false
        )
    }
}
