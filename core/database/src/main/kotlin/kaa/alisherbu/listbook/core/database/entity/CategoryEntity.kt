package kaa.alisherbu.listbook.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
class CategoryEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String? = null
)
