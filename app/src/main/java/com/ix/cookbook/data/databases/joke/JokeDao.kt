package com.ix.cookbook.data.databases.joke

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(jokeEntity: JokeEntity)

    @Query(value = "SELECT * FROM joke_table")
    fun readJoke(): Flow<List<JokeEntity>>
}
