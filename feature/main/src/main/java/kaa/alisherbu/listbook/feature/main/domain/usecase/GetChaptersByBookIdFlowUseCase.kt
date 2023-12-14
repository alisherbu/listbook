package kaa.alisherbu.listbook.feature.main.domain.usecase

import kaa.alisherbu.listbook.core.shared.model.Chapter
import kaa.alisherbu.listbook.feature.main.domain.model.ChapterResponse
import kaa.alisherbu.listbook.feature.main.domain.repository.ChaptersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChaptersByBookIdFlowUseCase @Inject constructor(
    private val chaptersRepository: ChaptersRepository
) {
    operator fun invoke(bookId: String): Flow<List<Chapter>> {
        return chaptersRepository.getChaptersByBookIdFlow(bookId).map(::toChapterList)
    }

    private fun toChapterList(responses: List<ChapterResponse>): List<Chapter> {
        return responses.map(::toChapter)
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
