package alex.lop.io.alexProject.data.local

import alex.lop.io.alexProject.data.model.FavoriteModel
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavoriteModel::class], version = 10, exportSchema = false)
@TypeConverters(MarvelConverters::class)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun MarvelDao() : MarvelDao
}