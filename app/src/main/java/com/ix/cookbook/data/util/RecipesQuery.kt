package com.ix.cookbook.data.util

import com.ix.cookbook.BuildConfig

data class RecipesQuery(
    val number: Int = 10,
    val apiKey: String = BuildConfig.apiKey,
    val type: String? = null,
    val diet: String? = null,
    val addRecipeInformation: Boolean = false,
    val fillIngredients: Boolean = false,
) {
    fun toQueryMap(): HashMap<String, String> {
        val query = HashMap<String, String>()
        with(query) {
            put("number", number.toString())
            put("apiKey", apiKey)
            type?.let { put("type", type) }
            diet?.let { put("diet", diet) }
            put("addRecipeInformation", addRecipeInformation.toString())
            put("fillIngredients", fillIngredients.toString())
        }
        return query
    }
}
