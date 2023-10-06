package com.ix.cookbook.data.datasources.joke

import com.ix.cookbook.data.models.joke.Joke
import com.ix.cookbook.data.services.JokeService
import com.ix.cookbook.util.Constants.Companion.apiKey
import javax.inject.Inject

class JokeRemoteDataSource @Inject constructor(
    private val jokeService: JokeService,
) {
    suspend fun getJoke(): Joke {
        return jokeService.getJoke(query = mapOf("apiKey" to apiKey))
    }
}
