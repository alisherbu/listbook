package kaa.alisherbu.listbook.feature.main.domain.usecase

import kaa.alisherbu.listbook.core.shared.model.Chapter
import kaa.alisherbu.listbook.feature.main.domain.model.ChapterResponse
import kaa.alisherbu.listbook.feature.main.domain.repository.ChaptersRepository
import javax.inject.Inject

class GetChaptersByBookIdUseCase @Inject constructor(
    private val chaptersRepository: ChaptersRepository
) {
    suspend operator fun invoke(bookId: String): List<Chapter> {
        return chaptersRepository.getChapters(bookId).map(::toChapter)
    }

    private fun toChapter(response: ChapterResponse): Chapter {
        return Chapter(
            id = response.id.toString(),
            bookId = response.bookId.toString(),
            audioUrl = response.audioUrl.toString(),
            name = response.name.toString(),
            isDownloaded = response.isDownloaded ?: false
        )
    }
}
