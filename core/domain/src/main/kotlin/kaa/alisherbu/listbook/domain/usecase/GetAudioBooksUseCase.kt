package kaa.alisherbu.listbook.domain.usecase

import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.domain.model.AudioBookResponse
import kaa.alisherbu.listbook.domain.repository.AudioBooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAudioBooksUseCase @Inject constructor(
    private val audioBooksRepository: AudioBooksRepository
) {
    operator fun invoke(): Flow<List<AudioBook>> {
        return audioBooksRepository.getAudioBooksFlow().map(::toAudioBookList)
    }

    private fun toAudioBookList(books: List<AudioBookResponse>): List<AudioBook> {
        return books.map(::toAudioBook)
    }

    private fun toAudioBook(book: AudioBookResponse): AudioBook {
        return AudioBook(
            id = book.id.toString(),
            name = book.name.toString(),
            audioUrl = book.audioUrl.toString(),
            isDownloaded = book.isDownloaded
        )
    }
}