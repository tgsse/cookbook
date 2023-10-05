package com.ix.cookbook.screens.joke

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.R
import com.ix.cookbook.ui.components.TopBar
import com.ix.cookbook.ui.theme.CookbookTheme

@Composable
fun FoodJokeScreen() {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopBar(
                title = stringResource(id = R.string.screen_food_joke),
            )
        },
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .semantics {
                    contentDescription =
                        context.getString(R.string.content_desc_food_joke_screen)
                },
            color = MaterialTheme.colorScheme.background,
        ) {
            Text(stringResource(R.string.screen_food_joke))
        }
    }
}

@Preview
@Composable
fun FoodJokePreview() {
    CookbookTheme {
        FoodJokeScreen()
    }
}
