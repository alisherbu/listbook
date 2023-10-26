package kaa.alisherbu.listbook.feature.home.domain.repository

import kaa.alisherbu.listbook.feature.home.data.model.AudioBookResponse
import kotlinx.coroutines.flow.Flow

internal interface AudioBooksRepository {
    fun subscribeToAudioBooks(): Flow<List<AudioBookResponse>>
}
