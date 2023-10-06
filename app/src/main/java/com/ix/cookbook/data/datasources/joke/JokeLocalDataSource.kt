package com.ix.cookbook.data.datasources.joke

import com.ix.cookbook.data.databases.joke.JokeDao
import com.ix.cookbook.data.databases.joke.JokeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JokeLocalDataSource @Inject constructor(
    private val jokeDao: JokeDao,
) {
    fun readJoke(): Flow<List<JokeEntity>> {
        return jokeDao.readJoke()
    }

    suspend fun insertJoke(jokeEntity: JokeEntity) {
        jokeDao.insertJoke(jokeEntity)
    }
}
