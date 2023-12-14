package kaa.alisherbu.listbook.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kaa.alisherbu.listbook.core.database.entity.AudioBookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioBooksDao {
    @Query("SELECT * FROM audio_books")
    fun getAudioBooks(): Flow<List<AudioBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(audioBooks: List<AudioBookEntity>)

    @Query("SELECT * FROM audio_books WHERE id= :id")
    suspend fun getAudioBook(id: String): AudioBookEntity

    @Update
    suspend fun update(entity: AudioBookEntity)
}
