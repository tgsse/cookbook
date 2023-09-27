package com.ix.cookbook.data.databases

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ix.cookbook.data.databases.DbUtil.Companion.recipesTable
import com.ix.cookbook.data.models.Recipes

@Entity(tableName = recipesTable)
class RecipesEntity(
    var recipes: Recipes,
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
