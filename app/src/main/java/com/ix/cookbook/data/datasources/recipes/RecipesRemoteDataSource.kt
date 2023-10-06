package com.ix.cookbook.data.datasources.recipes

import com.ix.cookbook.data.models.recipes.Recipes
import com.ix.cookbook.data.services.RecipesService
import javax.inject.Inject

class RecipesRemoteDataSource @Inject constructor(
    private val recipesService: RecipesService,
) {

    suspend fun getRecipes(queries: Map<String, String>): Recipes {
        return recipesService.getRecipes(queries)
    }
}
