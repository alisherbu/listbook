package kaa.alisherbu.listbook.domain.usecase

import kaa.alisherbu.listbook.core.shared.model.Chapter
import kaa.alisherbu.listbook.domain.model.ChapterResponse
import kaa.alisherbu.listbook.domain.repository.ChaptersRepository
import javax.inject.Inject

class GetChaptersByBookIdUseCase @Inject constructor(
    private val chaptersRepository: ChaptersRepository
) {
    suspend operator fun invoke(bookId: String): List<Chapter> {
        return chaptersRepository.getChapterByBookId(bookId).map(::toChapter)
    }

    private fun toChapter(response: ChapterResponse): Chapter {
        return Chapter(
            id = response.id.toString(),
            bookId = response.bookId.toString(),
            name = response.name.toString(),
            audioUrl = response.audioUrl.toString(),
            isDownloaded = response.isDownloaded
        )
    }
}
