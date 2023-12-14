package kaa.alisherbu.listbook.chapter.domain.usecase

import kaa.alisherbu.listbook.chapter.domain.model.ChapterResponse
import kaa.alisherbu.listbook.chapter.domain.repository.ChaptersRepository
import kaa.alisherbu.listbook.core.shared.model.Chapter
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
