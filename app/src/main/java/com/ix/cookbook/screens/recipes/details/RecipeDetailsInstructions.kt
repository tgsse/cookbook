package com.ix.cookbook.screens.recipes.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ix.cookbook.R
import com.ix.cookbook.data.models.AnalyzedInstruction
import com.ix.cookbook.data.models.dummyRecipe
import com.ix.cookbook.ui.theme.Colors
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

@Composable
fun RecipeDetailsInstructions(instructions: List<AnalyzedInstruction>) {
    instructions.forEach {
        it.steps.forEach { step ->
            Card() {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = stringResource(R.string.content_desc_step_done),
                        tint = Colors.green,
                    )
                    Spacer(Modifier.width(MaterialTheme.spacing.m))
                    Column {
                        Text(text = stringResource(id = R.string.label_step_number, step.number))
                        Text(text = step.step)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RecipeDetailsInstructionsPreview() {
    CookbookTheme {
        RecipeDetailsInstructions(dummyRecipe.analyzedInstructions)
    }
}
