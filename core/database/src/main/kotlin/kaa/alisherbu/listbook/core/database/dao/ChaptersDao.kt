package kaa.alisherbu.listbook.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kaa.alisherbu.listbook.core.database.entity.ChapterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChaptersDao {
    @Query("SELECT * FROM chapters")
    fun getAudioBooks(): Flow<List<ChapterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(audioBooks: List<ChapterEntity>)
}