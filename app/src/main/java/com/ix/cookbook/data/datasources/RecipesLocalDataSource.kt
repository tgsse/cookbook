package com.ix.cookbook.data.datasources

import com.ix.cookbook.data.databases.RecipesDao
import com.ix.cookbook.data.databases.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipesLocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao,
) {

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }
}
