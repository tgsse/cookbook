package com.ix.cookbook.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    @SerialName("aggregateLikes")
    val aggregateLikes: Int,
//    @SerialName("analyzedInstructions")
//    val analyzedInstructions: List<_AnalyzedInstruction>,
    @SerialName("cheap")
    val cheap: Boolean,
//    @SerialName("cookingMinutes")
//    val cookingMinutes: Int,
//    @SerialName("creditsText")
//    val creditsText: String,
//    @SerialName("cuisines")
//    val cuisines: List<String>,
    @SerialName("dairyFree")
    val dairyFree: Boolean,
//    @SerialName("diets")
//    val diets: List<String>,
//    @SerialName("dishTypes")
//    val dishTypes: List<String>,
    @SerialName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>,
//    @SerialName("gaps")
//    val gaps: String,
    @SerialName("glutenFree")
    val glutenFree: Boolean,
//    @SerialName("healthScore")
//    val healthScore: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
//    @SerialName("imageType")
//    val imageType: String,
//    @SerialName("license")
//    val license: String,
//    @SerialName("likes")
//    val likes: Int,
//    @SerialName("lowFodmap")
//    val lowFodmap: Boolean,
//    @SerialName("missedIngredientCount")
//    val missedIngredientCount: Int,
//    @SerialName("missedIngredients")
//    val missedIngredients: List<_MissedIngredient>,
//    @SerialName("occasions")
//    val occasions: List<Any>,
//    @SerialName("preparationMinutes")
//    val preparationMinutes: Int,
//    @SerialName("pricePerServing")
//    val pricePerServing: Double,
    @SerialName("readyInMinutes")
    val readyInMinutes: Int,
//    @SerialName("servings")
//    val servings: Int,
    @SerialName("sourceName")
    val sourceName: String,
    @SerialName("sourceUrl")
    val sourceUrl: String,
//    @SerialName("spoonacularSourceUrl")
//    val spoonacularSourceUrl: String,
    @SerialName("summary")
    val summary: String,
//    @SerialName("sustainable")
//    val sustainable: Boolean,
    @SerialName("title")
    val title: String,
//    @SerialName("unusedIngredients")
//    val unusedIngredients: List<Any>,
//    @SerialName("usedIngredientCount")
//    val usedIngredientCount: Int,
//    @SerialName("usedIngredients")
//    val usedIngredients: List<Any>,
    @SerialName("vegan")
    val vegan: Boolean,
//    @SerialName("vegetarian")
//    val vegetarian: Boolean,
    @SerialName("veryHealthy")
    val veryHealthy: Boolean,
//    @SerialName("veryPopular")
//    val veryPopular: Boolean,
//    @SerialName("weightWatcherSmartPoints")
//    val weightWatcherSmartPoints: Int,
)
