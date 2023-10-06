package com.ix.cookbook.screens.joke

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ix.cookbook.R
import com.ix.cookbook.data.databases.joke.JokeEntity
import com.ix.cookbook.data.models.joke.Joke
import com.ix.cookbook.data.repositories.JokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JokeState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val joke: Joke? = null,
)

sealed class JokeEvent {
    object Init : JokeEvent()
    object GetNewJoke : JokeEvent()
}

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val repository: JokeRepository,
    application: Application,
) : AndroidViewModel(application = application) {

    private val _state = MutableStateFlow(JokeState())
    val state = _state.asStateFlow()

    private val uiEventChannel = Channel<UiEvent>()
    val uiEvents = uiEventChannel.receiveAsFlow()

    fun onEvent(event: JokeEvent) {
        when (event) {
            is JokeEvent.Init -> init()
            is JokeEvent.GetNewJoke -> getNewJoke()
        }
    }

    private fun init() {
        viewModelScope.launch {
            loadJoke()
        }
    }

    private fun getNewJoke() {
        viewModelScope.launch {
            fetchJoke()
        }
    }

    private suspend fun fetchJoke() {
        if (!state.value.isError) {
            _state.update { uiState -> uiState.copy(isLoading = true) }
        }
        try {
            val joke = repository.remote.getJoke()
            _state.update { uiState -> uiState.copy(joke = joke, isLoading = false) }
            cacheJoke(joke)
        } catch (e: Exception) {
            _state.update { uiState -> uiState.copy(isError = true, isLoading = false) }
            showError(e)
        }
    }

    private suspend fun cacheJoke(joke: Joke) {
        repository.local.insertJoke(JokeEntity(joke))
    }

    private suspend fun loadJoke() {
        val joke = repository.local.readJoke().firstOrNull()

        joke?.let { value ->
            if (value.isNotEmpty()) {
                _state.update { uiState ->
                    uiState.copy(
                        joke = value[0].joke,
                        isLoading = false,
                    )
                }
            } else {
                fetchJoke()
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
    }
}
