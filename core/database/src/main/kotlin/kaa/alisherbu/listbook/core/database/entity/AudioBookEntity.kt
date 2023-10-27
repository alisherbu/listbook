package kaa.alisherbu.listbook.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audio_books")
class AudioBookEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("name")
    val name: String? = null,
    @ColumnInfo("audioUrl")
    val audioUrl: String? = null
)