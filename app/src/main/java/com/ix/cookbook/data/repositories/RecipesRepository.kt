package com.ix.cookbook.data.repositories

import com.ix.cookbook.data.datasources.RecipesRemoteDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RecipesRepository @Inject constructor(
    remoteDataSource: RecipesRemoteDataSource,
//    localDataSource: RecipesLocalDataSource,
) {

    val remote = remoteDataSource
}
