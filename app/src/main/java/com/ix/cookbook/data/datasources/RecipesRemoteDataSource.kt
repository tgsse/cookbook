package com.ix.cookbook.data.datasources

import com.ix.cookbook.data.models.Recipes
import com.ix.cookbook.data.services.RecipesService
import retrofit2.Response
import javax.inject.Inject

class RecipesRemoteDataSource @Inject constructor(
    private val recipesService: RecipesService,
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<Recipes> {
        return recipesService.getRecipes(queries)
    }
}
