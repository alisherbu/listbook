package kaa.alisherbu.listbook.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.core.database.ListbookDatabase
import kaa.alisherbu.listbook.core.database.dao.AudioBooksDao

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): ListbookDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = ListbookDatabase::class.java,
            name = DATABASE_NAME
        ).build()
    }

    companion object {
        private const val DATABASE_NAME = "listbook_database"
    }

    @Provides
    fun provideAudioBooksDao(database: ListbookDatabase): AudioBooksDao {
        return database.audioBooksDao()
    }
}