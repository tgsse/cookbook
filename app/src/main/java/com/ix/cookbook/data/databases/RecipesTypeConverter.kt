package com.ix.cookbook.data.databases

import androidx.room.TypeConverter
import com.ix.cookbook.data.models.Recipe
import com.ix.cookbook.data.models.Recipes
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RecipesTypeConverter {

    @TypeConverter
    fun recipesToString(recipes: Recipes): String {
        return Json.encodeToString(recipes)
    }

    @TypeConverter
    fun stringToRecipes(recipes: String): Recipes {
        return Json.decodeFromString(recipes)
    }

    @TypeConverter
    fun recipeToString(recipe: Recipe): String {
        return Json.encodeToString(recipe)
    }

    @TypeConverter
    fun stringToRecipe(recipe: String): Recipe {
        return Json.decodeFromString(recipe)
    }
}
