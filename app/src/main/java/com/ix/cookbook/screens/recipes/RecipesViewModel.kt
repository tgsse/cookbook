package com.ix.cookbook.screens.recipes

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ix.cookbook.R
import com.ix.cookbook.data.databases.RecipesEntity
import com.ix.cookbook.data.models.Recipes
import com.ix.cookbook.data.repositories.RecipesRepository
import com.ix.cookbook.data.util.RecipesQuery
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
)

sealed class RecipesEvent {
    object Init : RecipesEvent()
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
        }
    }

    private fun init() {
        loadRecipesFromCache()
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

    private fun fetchRecipesFromRemote() {
        val queryMap = RecipesQuery(
            number = 1,
            type = "snack",
            diet = "vegan",
            addRecipeInformation = true,
            fillIngredients = true,
        ).toQueryMap()

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
