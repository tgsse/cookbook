package com.ix.cookbook.data.databases.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ix.cookbook.data.databases.DbUtil.Companion.favoriteRecipesTable
import com.ix.cookbook.data.models.Recipe

@Entity(tableName = favoriteRecipesTable)
class FavoriteRecipeEntity(
    var recipe: Recipe,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "external_id")
    var externalId: Int = recipe.id
}
