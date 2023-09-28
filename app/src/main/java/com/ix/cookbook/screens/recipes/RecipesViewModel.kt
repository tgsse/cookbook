package com.ix.cookbook.screens.recipes

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ix.cookbook.R
import com.ix.cookbook.data.databases.RecipesEntity
import com.ix.cookbook.data.models.Recipes
import com.ix.cookbook.data.repositories.RecipesRepository
import com.ix.cookbook.data.requestUtil.RecipesQuery
import com.ix.cookbook.data.requestUtil.filters.DietTypeFilter
import com.ix.cookbook.data.requestUtil.filters.MealTypeFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipesState(
    val isLoading: Boolean = false,
    val recipes: Recipes = Recipes(),
    var selectedMealFilter: MealTypeFilter? = null,
    var selectedDietFilter: DietTypeFilter? = null,
)

sealed class RecipesEvent {
    object Init : RecipesEvent()
    data class Filter(
        val mealFilter: MealTypeFilter? = null,
        val dietFilter: DietTypeFilter? = null,
    ) : RecipesEvent()
}

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repository: RecipesRepository,
    application: Application,
) : AndroidViewModel(application = application) {

    private val _state = MutableStateFlow(RecipesState())
    val state = _state.asStateFlow()

    private val uiEventChannel = Channel<UiEvent>()
    val uiEvents = uiEventChannel.receiveAsFlow()

    fun onEvent(event: RecipesEvent) {
        when (event) {
            is RecipesEvent.Init -> init()
            is RecipesEvent.Filter -> applyFilters(event.mealFilter, event.dietFilter)
        }
    }

    private fun init() {
        loadRecipesFromCache()
//        fetchRecipesFromRemote()
    }

    private fun applyFilters(mealType: MealTypeFilter?, dietType: DietTypeFilter?) {
        if (mealType == state.value.selectedMealFilter && dietType == state.value.selectedDietFilter) {
            return
        }
        _state.update { uiState ->
            uiState.copy(
                selectedMealFilter = mealType,
                selectedDietFilter = dietType,
            )
        }
        fetchRecipesFromRemote()
    }

    private fun cacheRecipes(recipes: Recipes) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(RecipesEntity(recipes))
        }

    private fun loadRecipesFromCache() {
        viewModelScope.launch {
            val recipes = repository.local.readRecipes().firstOrNull()
            recipes?.let { value ->
                if (value.isNotEmpty()) {
                    // TODO: hardcoded access to first result
                    _state.update { uiState -> uiState.copy(recipes = value[0].recipes) }
                } else {
                    fetchRecipesFromRemote()
                }
            }
        }
    }

    private fun buildQueryMap(): HashMap<String, String> {
        return RecipesQuery(
            number = 1,
            type = state.value.selectedMealFilter?.id,
            diet = state.value.selectedDietFilter?.id,
            addRecipeInformation = true,
            fillIngredients = true,
        ).toQueryMap()
    }

    private fun fetchRecipesFromRemote() {
        val queryMap = buildQueryMap()
        viewModelScope.launch {
            try {
                _state.update { uiState -> uiState.copy(isLoading = true) }
                val recipes = repository.remote.getRecipes(queries = queryMap)
                _state.update { uiState -> uiState.copy(recipes = recipes) }
                if (recipes.items.isNotEmpty()) {
                    cacheRecipes(recipes)
                }
            } catch (e: Exception) {
                showError(e)
            } finally {
                _state.update { uiState -> uiState.copy(isLoading = false) }
            }
        }
    }

    private fun showError(e: Exception) {
        Log.e(this.javaClass.toString(), e.toString())
        viewModelScope.launch {
            uiEventChannel.send(
                element = UiEvent.ShowMessage(
                    message = e.message
                        ?: getApplication<Application>().resources.getString(R.string.error_default),
                ),
            )
        }
    }

    sealed class UiEvent {
        data class ShowMessage(val message: String) : UiEvent()
//        data class NavigateToDetail(val recipe: Recipe) : UiEvent()
    }
}

val defaultMealTypeFilters = listOf(
    MealTypeFilter("Breakfast", "breakfast"),
    MealTypeFilter("Appetizer", "appetizer"),
    MealTypeFilter("Soup", "soup"),
    MealTypeFilter("Main", "main course"),
    MealTypeFilter("Dessert", "dessert"),
    MealTypeFilter("Snack", "snack"),
    MealTypeFilter("Side", "side dish"),
    MealTypeFilter("Salad", "salad"),
    MealTypeFilter("Bread", "bread"),
    MealTypeFilter("Sauce", "sauce"),
    MealTypeFilter("Marinade", "marinade"),
    MealTypeFilter("Fingerfood", "fingerfood"),
    MealTypeFilter("Beverage", "beverage"),
    MealTypeFilter("Drink", "drink"),
)

val defaultDietTypeFilters = listOf(
    DietTypeFilter("Gluten Free", "gluten free"),
    DietTypeFilter("Keto", "ketogenic"),
    DietTypeFilter("Vegetarian", "vegetarian"),
    DietTypeFilter("Lacto-vegetarian", "lacto-vegetarian"),
    DietTypeFilter("Ovo-vegetarian", "ovo-vegetarian"),
    DietTypeFilter("vegan", "vegan"),
    DietTypeFilter("Pescetarian", "pescetarian"),
    DietTypeFilter("Paleo", "paleo"),
    DietTypeFilter("Primal", "primal"),
    DietTypeFilter("Low FODMAP", "low fodmap"),
    DietTypeFilter("Whole30", "whole30"),
)
