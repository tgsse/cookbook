package com.ix.cookbook.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ix.cookbook.data.requestUtil.filters.DietTypeFilter
import com.ix.cookbook.data.requestUtil.filters.MealTypeFilter
import com.ix.cookbook.screens.recipes.defaultDietTypeFilters
import com.ix.cookbook.screens.recipes.defaultMealTypeFilters
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    companion object {
        const val PREF_KEY_MEAL_TYPE = "mealType"
        const val PREF_KEY_DIET_TYPE = "dietType"
        private const val FILE_NAME = "settings"
    }

    private object Keys {
        val selectedMealType = stringPreferencesKey(PREF_KEY_MEAL_TYPE)

        val selectedDietType = stringPreferencesKey(PREF_KEY_DIET_TYPE)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FILE_NAME)

    suspend fun saveMealAndDietFilter(mealFilter: MealTypeFilter?, dietFilter: DietTypeFilter?) {
        context.dataStore.edit { settings ->
            settings[Keys.selectedMealType] = mealFilter?.id.orEmpty()
            settings[Keys.selectedDietType] = dietFilter?.id.orEmpty()
        }
    }

    val readMealAndDietFilter: Flow<MealAndDietFilters> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val mealType = if (!prefs[Keys.selectedMealType].isNullOrBlank()) {
                defaultMealTypeFilters.find { it.id == prefs[Keys.selectedMealType] }
            } else null

            val dietType = if (!prefs[Keys.selectedDietType].isNullOrBlank()) {
                defaultDietTypeFilters.find { it.id == prefs[Keys.selectedDietType] }
            } else null

            MealAndDietFilters(
                selectedMealType = mealType,
                selectedDietType = dietType,
            )
        }
}

data class MealAndDietFilters(
    val selectedMealType: MealTypeFilter?,
    val selectedDietType: DietTypeFilter?,
)
