package kaa.alisherbu.listbook.core.data.util

import kaa.alisherbu.listbook.core.database.entity.ChapterEntity
import kaa.alisherbu.listbook.domain.model.ChapterResponse

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