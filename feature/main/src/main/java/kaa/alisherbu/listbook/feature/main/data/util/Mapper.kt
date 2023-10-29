package kaa.alisherbu.listbook.feature.main.data.util

import kaa.alisherbu.listbook.core.database.entity.ChapterEntity
import kaa.alisherbu.listbook.feature.main.domain.model.ChapterResponse


internal fun toChapterResponse(entity: ChapterEntity): ChapterResponse {
    return ChapterResponse(
        id = entity.id,
        bookId = entity.bookId,
        name = entity.name,
        audioUrl = entity.audioUrl,
        isDownloaded = entity.isDownloaded
    )
}

internal fun toChapterResponseList(entities: List<ChapterEntity>): List<ChapterResponse> {
    return entities.map(::toChapterResponse)
}