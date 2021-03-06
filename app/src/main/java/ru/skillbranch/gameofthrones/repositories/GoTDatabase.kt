package ru.skillbranch.gameofthrones.repositories

import android.content.Context
import androidx.room.*
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.data.local.entities.Character

const val DATABASE_SCHEMA_VERSION = 1
const val DATABASE_NAME = "local-db"

@Database(version = DATABASE_SCHEMA_VERSION,
        entities = [House::class, Character::class])
@TypeConverters(Converters::class)
abstract class GoTDatabase: RoomDatabase() {

    // Insert DAO below
    abstract fun houseDAO(): HouseDAO
    abstract fun characterDAO(): CharacterDAO

    companion object {
        private var instance: GoTDatabase? = null

        fun getInstance(context: Context):GoTDatabase {
            if (instance == null) {
                instance = createDatabase(context)
            }
            return instance!!
        }

        // TODO: move away main thread allowance (solution is coroutines!) !!!
        private fun createDatabase(context: Context): GoTDatabase {
            return Room.databaseBuilder(context, GoTDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries() // very dangerous!!!
                .build()
        }
    }
}
object Converters {

    @TypeConverter
    fun fromString(value: String): List<String> = value.split(";")

    @TypeConverter
    fun fromArrayList(list: List<String>) = list.joinToString(";")
}