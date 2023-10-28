package kaa.alisherbu.listbook.domain.usecase

import kaa.alisherbu.listbook.core.shared.model.Chapter
import kaa.alisherbu.listbook.domain.model.ChapterResponse
import kaa.alisherbu.listbook.domain.repository.ChaptersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChaptersUseCase @Inject constructor(
    private val chaptersRepository: ChaptersRepository
) {
    operator fun invoke(): Flow<List<Chapter>> {
        return chaptersRepository.getChaptersFlow().map(::toChapterList)
    }

    private fun toChapterList(responses: List<ChapterResponse>): List<Chapter> {
        return responses.map(::toChapter)
    }

    private fun toChapter(response: ChapterResponse): Chapter {
        return Chapter(
            bookId = response.bookId.toString(),
            name = response.name.toString(),
            audioUrl = response.audioUrl.toString(),
            isDownloaded = response.isDownloaded
        )
    }
}