package com.ix.cookbook.data.services

import com.ix.cookbook.data.models.joke.Joke
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface JokeService {

    @GET("/food/jokes/random")
    suspend fun getJoke(
        @QueryMap query: Map<String, String>,
    ): Joke
}
