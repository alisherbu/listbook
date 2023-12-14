package kaa.alisherbu.listbook.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kaa.alisherbu.listbook.core.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<CategoryEntity>)

    @Query("SELECT * FROM categories WHERE id= :id")
    suspend fun getCategory(id: String): CategoryEntity

    @Update
    suspend fun update(entity: CategoryEntity)
}
