package com.ix.cookbook.data.databases.joke

import androidx.room.TypeConverter
import com.ix.cookbook.data.models.joke.Joke
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class JokeTypeConverter {

    @TypeConverter
    fun jokeToString(joke: Joke): String {
        return Json.encodeToString(joke)
    }

    @TypeConverter
    fun stringToJoke(joke: String): Joke {
        return Json.decodeFromString(joke)
    }
}
