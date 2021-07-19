package kg.unicapp.weatherapi.storage

import android.content.Context
import androidx.room.*
import kg.unicapp.weatherapi.models.ForeCast

@Database(
    entities = [ForeCast::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ModelsConverter::class, CollectionsConverter::class)
abstract class ForeCastDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForeCastDao

    companion object {
        const val DB_NAME = "forecastDb"

        private var DB: ForeCastDatabase? = null

        fun getInstance(context: Context): ForeCastDatabase {
            if (DB == null) {
                DB = Room.databaseBuilder(
                    context,
                    ForeCastDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DB!!
        }
    }
}