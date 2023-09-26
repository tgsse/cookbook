package com.ix.cookbook.data.services

import com.ix.cookbook.data.models.Recipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RecipesService {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>,
    ): Response<Recipes>
}
