package kaa.alisherbu.listbook.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class ChapterEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "bookId")
    val bookId: String? = null,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "audioUrl")
    val audioUrl: String? = null,
    @ColumnInfo(name = "headerImage")
    val headerImage: String? = null,
    @ColumnInfo(name = "isDownloaded")
    val isDownloaded: Boolean = false
)
