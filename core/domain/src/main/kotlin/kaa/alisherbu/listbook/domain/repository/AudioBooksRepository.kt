package kaa.alisherbu.listbook.domain.repository

import kaa.alisherbu.listbook.domain.model.AudioBookResponse
import kotlinx.coroutines.flow.Flow

interface AudioBooksRepository {
    fun getAudioBooksFlow(): Flow<List<AudioBookResponse>>

    fun syncWithRemote()
}