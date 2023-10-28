package kaa.alisherbu.listbook.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class ChapterEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("bookId")
    val bookId: String? = null,
    @ColumnInfo("name")
    val name: String? = null,
    @ColumnInfo("audioUrl")
    val audioUrl: String? = null,
    @ColumnInfo("headerImage")
    val headerImage: String? = null,
    @ColumnInfo(name = "isDownloaded")
    val isDownloaded: Boolean = false
)