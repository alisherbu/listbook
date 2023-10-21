package kaa.alisherbu.listbook.feature.home.store

import kaa.alisherbu.listbook.feature.home.domain.model.AudioBook

class HomeState(
    val audioBooks: List<AudioBook> = emptyList()
)