package kaa.alisherbu.listbook.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kaa.alisherbu.listbook.core.database.dao.AudioBooksDao
import kaa.alisherbu.listbook.core.database.dao.ChaptersDao
import kaa.alisherbu.listbook.core.database.entity.AudioBookEntity
import kaa.alisherbu.listbook.core.database.entity.ChapterEntity

@Database(entities = [AudioBookEntity::class, ChapterEntity::class], version = 1)
abstract class ListbookDatabase : RoomDatabase() {
    abstract fun audioBooksDao(): AudioBooksDao
    abstract fun chaptersDao(): ChaptersDao
}
