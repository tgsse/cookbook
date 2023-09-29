package com.ix.cookbook.data.requestUtil

import com.ix.cookbook.BuildConfig

data class RecipesQuery(
    val number: Int = 10,
    val apiKey: String = BuildConfig.apiKey,
    val type: String? = null,
    val diet: String? = null,
    val addRecipeInformation: Boolean = false,
    val fillIngredients: Boolean = false,
    val searchQuery: String? = null,
) {
    fun toQueryMap(): HashMap<String, String> {
        val query = HashMap<String, String>()
        with(query) {
            put("number", number.toString())
            put("apiKey", apiKey)
            type?.let { put("type", type) }
            diet?.let { put("diet", diet) }
            searchQuery?.let { put("query", searchQuery) }
            put("addRecipeInformation", addRecipeInformation.toString())
            put("fillIngredients", fillIngredients.toString())
        }
        return query
    }
}
