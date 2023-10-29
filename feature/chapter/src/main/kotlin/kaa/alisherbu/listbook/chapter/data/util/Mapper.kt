package kaa.alisherbu.listbook.chapter.data.util

import kaa.alisherbu.listbook.chapter.domain.model.ChapterResponse
import kaa.alisherbu.listbook.core.database.entity.ChapterEntity

internal fun toChapterResponseList(entities: List<ChapterEntity>): List<ChapterResponse> {
    return entities.map(::toChapterResponse)
}

internal fun toChapterResponse(entity: ChapterEntity): ChapterResponse {
    return ChapterResponse(
        id = entity.id,
        bookId = entity.bookId,
        name = entity.name
    )
}