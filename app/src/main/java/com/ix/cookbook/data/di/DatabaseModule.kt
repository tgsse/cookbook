package com.ix.cookbook.data.di

import android.content.Context
import androidx.room.Room
import com.ix.cookbook.data.databases.DbUtil.Companion.recipesDatabase
import com.ix.cookbook.data.databases.RecipesDatabase
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
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context = context,
        klass = RecipesDatabase::class.java,
        name = recipesDatabase,
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipesDao()
}
