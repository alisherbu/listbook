package kaa.alisherbu.listbook.feature.home.domain.usecase

import kaa.alisherbu.listbook.feature.home.data.model.AudioBookResponse
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.feature.home.domain.repository.AudioBooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class LoadAudioBooksUseCase @Inject constructor(
    private val audioBookRepository: AudioBooksRepository
) {
    operator fun invoke(): Flow<List<AudioBook>> {
        return audioBookRepository.subscribeToAudioBooks().map(::toAudioBookList)
    }

    private fun toAudioBookList(books: List<AudioBookResponse>): List<AudioBook> {
        return books.map { AudioBook(it.id.toString(), it.name.toString()) }
    }
}