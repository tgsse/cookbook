package com.ix.cookbook.screens.recipes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ix.cookbook.R
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

@Composable
fun ErrorView(
    error: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.spacing.xl)
            .alpha(.6f),
    ) {
        Icon(
            imageVector = Icons.Outlined.SentimentDissatisfied,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = MaterialTheme.spacing.m)
                .size(80.dp),
        )
        Text(error)
    }
}

@Preview
@Composable
private fun ErrorViewPreview() {
    CookbookTheme {
        ErrorView(stringResource(R.string.error_recipes_not_found))
    }
}
