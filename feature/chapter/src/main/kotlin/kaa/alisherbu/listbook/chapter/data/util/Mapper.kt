package kaa.alisherbu.listbook.chapter.data.util

import kaa.alisherbu.listbook.chapter.domain.model.ChapterResponse
import kaa.alisherbu.listbook.core.database.entity.ChapterEntity

internal fun toChapterResponse(entity: ChapterEntity): ChapterResponse {
    return ChapterResponse(
        id = entity.id,
        bookId = entity.bookId,
        name = entity.name,
        isDownloaded = entity.isDownloaded
    )
}
