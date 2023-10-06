package com.ix.cookbook.data.repositories

import com.ix.cookbook.data.datasources.joke.JokeLocalDataSource
import com.ix.cookbook.data.datasources.joke.JokeRemoteDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class JokeRepository @Inject constructor(
    remoteDataSource: JokeRemoteDataSource,
    localDataSource: JokeLocalDataSource,
) {
    val remote = remoteDataSource
    val local = localDataSource
}
