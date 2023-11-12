package alex.lop.io.alexProject.data.local

import alex.lop.io.alexProject.data.model.character.CharacterModel
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface MarvelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterModel : CharacterModel) : Long

    @Query("SELECT * FROM characterModel ORDER BY id")
    fun getAll(): Flow<List<CharacterModel>>

    @Delete
    suspend fun delete(characterModel : CharacterModel)

    @Query("Select COUNT(*) from characterModel WHERE id = :characterId")
    suspend fun searchFavorite(characterId: Int) : Boolean
}