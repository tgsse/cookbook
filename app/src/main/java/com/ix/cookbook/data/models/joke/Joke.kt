package com.ix.cookbook.data.models.joke

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Joke(
    @SerialName("text")
    val text: String,
)
