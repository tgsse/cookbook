package com.ix.cookbook.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipes(
//    @SerialName("number")
//    val number: Int,
//    @SerialName("offset")
//    val offset: Int,
    @SerialName("results")
    val items: List<Recipe> = emptyList(),
//    @SerialName("totalResults")
//    val totalResults: Int
)
