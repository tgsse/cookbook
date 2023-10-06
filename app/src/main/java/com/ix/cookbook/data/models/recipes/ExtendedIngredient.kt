package com.ix.cookbook.data.models.recipes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExtendedIngredient(
//    @SerialName("aisle")
//    val aisle: String,
//    @SerialName("amount")
//    val amount: Double,
//    @SerialName("consistency")
//    val consistency: String,
//    @SerialName("id")
//    val id: Int,
    @SerialName("image")
    val image: String?,
//    @SerialName("measures")
//    val measures: Measures,
//    @SerialName("meta")
//    val meta: List<String>,
    @SerialName("name")
    val name: String,
//    @SerialName("nameClean")
//    val nameClean: String,
//    @SerialName("original")
    val original: String,
//    @SerialName("originalName")
//    val originalName: String,
//    @SerialName("unit")
//    val unit: String,
)
