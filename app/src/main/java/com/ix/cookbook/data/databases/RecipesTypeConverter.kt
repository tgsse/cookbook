package com.ix.cookbook.data.databases

import androidx.room.TypeConverter
import com.ix.cookbook.data.models.Recipes
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RecipesTypeConverter {

    @TypeConverter
    fun recipeToString(recipes: Recipes): String {
        return Json.encodeToString(recipes)
    }

    @TypeConverter
    fun stringToRecipe(recipes: String): Recipes {
        return Json.decodeFromString(recipes)
    }
}
