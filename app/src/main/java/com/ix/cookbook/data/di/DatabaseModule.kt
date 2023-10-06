package com.ix.cookbook.data.di

import android.content.Context
import androidx.room.Room
import com.ix.cookbook.data.databases.joke.JokeDatabase
import com.ix.cookbook.data.databases.joke.JokeDbUtil.Companion.jokeDatabase
import com.ix.cookbook.data.databases.recipes.RecipesDatabase
import com.ix.cookbook.data.databases.recipes.RecipesDbUtil.Companion.recipesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRecipeDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context = context,
        klass = RecipesDatabase::class.java,
        name = recipesDatabase,
    ).build()

    @Singleton
    @Provides
    fun provideRecipeDao(database: RecipesDatabase) = database.recipesDao()

    @Singleton
    @Provides
    fun provideJokeDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context = context,
        klass = JokeDatabase::class.java,
        name = jokeDatabase,
    ).build()

    @Singleton
    @Provides
    fun provideJokeDao(database: JokeDatabase) = database.jokeDao()
}
