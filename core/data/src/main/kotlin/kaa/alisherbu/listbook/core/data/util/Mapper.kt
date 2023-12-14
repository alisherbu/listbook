package kaa.alisherbu.listbook.core.data.util

import kaa.alisherbu.listbook.core.database.entity.ChapterEntity
import kaa.alisherbu.listbook.domain.model.ChapterResponse

internal fun toChapterResponse(entity: ChapterEntity): ChapterResponse {
    return ChapterResponse(
        id = entity.id,
        bookId = entity.bookId,
        name = entity.name,
        isDownloaded = entity.isDownloaded
    )
}
