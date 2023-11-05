package alex.lop.io.alexProject.data.local

import alex.lop.io.alexProject.data.model.character.CharacterModel
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CharacterModel::class], version = 1, exportSchema = false)
@TypeConverters(MarvelConverters::class)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun MarvelDao(): MarvelDao

}