package com.ix.cookbook.screens.joke

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.flowWithLifecycle
import com.ix.cookbook.R
import com.ix.cookbook.data.models.joke.Joke
import com.ix.cookbook.screens.recipes.components.ErrorView
import com.ix.cookbook.ui.components.TopBar
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

@Composable
fun JokeScreen(
    viewModel: JokeViewModel = hiltViewModel(),
) {
    val screenContentDescription = stringResource(id = R.string.content_desc_food_joke_screen)
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()


    fun shareJoke(joke: Joke) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, joke.text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

    DisposableEffect(lifecycleOwner) {
        val reloadWhenResumed = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onEvent(event = JokeEvent.Init)
            }
        }
        lifecycleOwner.lifecycle.addObserver(reloadWhenResumed)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(reloadWhenResumed)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvents.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED,
        ).collect { event ->
            when (event) {
                is JokeViewModel.UiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopBar(
                title = stringResource(id = R.string.screen_food_joke),
                actions = {
                    IconButton(
                        onClick = {
                            state.joke?.let { shareJoke(it) }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = stringResource(
                                R.string.content_desc_share_joke,
                            ),
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .semantics {
                    contentDescription = screenContentDescription
                },
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(MaterialTheme.spacing.m),
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(40.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .7f),
                        trackColor = MaterialTheme.colorScheme.secondary,
                    )
                } else {
                    if (state.isError || state.joke == null) {
                        ErrorView(error = stringResource(R.string.error_while_loading_joke))
                    } else {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(
                                        horizontal = MaterialTheme.spacing.m,
                                        vertical = MaterialTheme.spacing.l,
                                    ),
                            ) {
                                Text(
                                    text = state.joke!!.text,
                                    style = MaterialTheme.typography.displayMedium,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.m))
                        Button(onClick = { viewModel.onEvent(JokeEvent.GetNewJoke) }) {
                            Text(stringResource(id = R.string.label_get_new_joke))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FoodJokePreview() {
    CookbookTheme {
        JokeScreen()
    }
}
