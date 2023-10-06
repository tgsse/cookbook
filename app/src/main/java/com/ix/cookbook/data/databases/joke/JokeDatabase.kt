package com.ix.cookbook.data.databases.joke

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [JokeEntity::class],
    version = 1, // NOTE: this needs to be updated if the model changes. Migration is required as well
    exportSchema = false,
)
@TypeConverters(JokeTypeConverter::class)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}
