package com.ix.cookbook.data.databases.joke

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ix.cookbook.data.databases.joke.JokeDbUtil.Companion.jokeTable
import com.ix.cookbook.data.models.joke.Joke

@Entity(tableName = jokeTable)
class JokeEntity(
    val joke: Joke,
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
