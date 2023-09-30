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
import com.ix.cookbook.data.requestUtil.filters.QueryFilter
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
        private const val FILE_NAME = "settings"

        const val PREF_KEY_MEAL_TYPE = "mealType"
        const val PREF_KEY_DIET_TYPE = "dietType"
        const val PREF_KEY_QUERY = "query"
    }

    private object Keys {
        val selectedMealType = stringPreferencesKey(PREF_KEY_MEAL_TYPE)

        val selectedDietType = stringPreferencesKey(PREF_KEY_DIET_TYPE)

        val selectedQuery = stringPreferencesKey(PREF_KEY_QUERY)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FILE_NAME)

    suspend fun saveFilters(
        mealFilter: MealTypeFilter?,
        dietFilter: DietTypeFilter?,
        queryFilter: QueryFilter?,
    ) {
        context.dataStore.edit { settings ->
            settings[Keys.selectedMealType] = mealFilter?.value.orEmpty()
            settings[Keys.selectedDietType] = dietFilter?.value.orEmpty()
            settings[Keys.selectedQuery] = queryFilter?.value.orEmpty()
        }
    }

    val filters: Flow<RecipesFilters> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val mealType = if (!prefs[Keys.selectedMealType].isNullOrBlank()) {
                defaultMealTypeFilters.find { it.value == prefs[Keys.selectedMealType] }
            } else {
                null
            }

            val dietType = if (!prefs[Keys.selectedDietType].isNullOrBlank()) {
                defaultDietTypeFilters.find { it.value == prefs[Keys.selectedDietType] }
            } else {
                null
            }

            val query = if (!prefs[Keys.selectedQuery].isNullOrBlank()) {
                QueryFilter(value = prefs[Keys.selectedQuery]!!)
            } else {
                null
            }

            RecipesFilters(
                selectedMealType = mealType,
                selectedDietType = dietType,
                selectedQuery = query,
            )
        }
}

data class RecipesFilters(
    val selectedMealType: MealTypeFilter?,
    val selectedDietType: DietTypeFilter?,
    val selectedQuery: QueryFilter?,
)
