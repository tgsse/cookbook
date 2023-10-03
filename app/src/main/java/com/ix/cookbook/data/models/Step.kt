package com.ix.cookbook.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Step(
//    @SerialName("equipment")
//    val equipment: List<Equipment>,
//    @SerialName("ingredients")
//    val ingredients: List<Ingredient>,
    @SerialName("number")
    val number: Int,
    @SerialName("step")
    val step: String,
)
