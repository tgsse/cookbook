package com.ix.cookbook.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.R
import com.ix.cookbook.ui.theme.CookbookTheme

@Composable
fun BackIconButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = stringResource(
                R.string.content_desc_navigate_back,
            ),
        )
    }
}

@Preview
@Composable
fun BackIconPreview() {
    CookbookTheme {
        BackIconButton(onClick = {})
    }
}
