package kaa.alisherbu.listbook.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kaa.alisherbu.listbook.core.database.entity.ChapterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChaptersDao {
    @Query("SELECT * FROM chapters WHERE bookId=:bookId")
    fun getChaptersFlow(bookId: String): Flow<List<ChapterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(audioBooks: List<ChapterEntity>)

    @Query("SELECT * FROM chapters WHERE bookId=:bookId")
    suspend fun getChaptersByBookId(bookId: String): List<ChapterEntity>

    @Query("SELECT * FROM chapters WHERE id= :id")
    suspend fun getChapterById(id: String): ChapterEntity

    @Update
    suspend fun update(entity: ChapterEntity)
}
