package com.ix.cookbook.data.databases.recipes.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ix.cookbook.data.databases.recipes.RecipesDbUtil.Companion.favoriteRecipesTable
import com.ix.cookbook.data.models.recipes.Recipe

@Entity(tableName = favoriteRecipesTable)
class FavoriteRecipeEntity(
    var recipe: Recipe,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "external_id")
    var externalId: Int = recipe.id
}
