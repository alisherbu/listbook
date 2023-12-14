package kaa.alisherbu.listbook.feature.home.store

import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.core.shared.model.Category

data class HomeState(
    val audioBooks: List<AudioBook> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isRefreshing: Boolean = false
)
