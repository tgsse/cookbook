package com.ix.cookbook.screens.recipes.components.recipeitem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ix.cookbook.R
import com.ix.cookbook.ui.theme.Colors
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

private val gradientColors = listOf(Color.Black, Colors.gray)
val cardHeight = 180.dp

@Composable
fun RecipeItem() {
    Card(
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.typography.bodySmall.color,
        ),
        modifier = Modifier
            .height(cardHeight),
    ) {
        Row {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .width(160.dp)
                    .fillMaxHeight(),
            )
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.s),
            ) {
                Text(
                    text = "Recipe Title",
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.s))
                RecipeAttributes()
                Text(
                    text = "Bonbon chocolate cake jelly-o toffee oat cake. Pudding sweet soufflé cupcake oat cake candy canes. Chocolate bear claw chocolate bar toffee muffin pudding biscuit liquorice. Bear claw macaroon pastry sugar plum ice cream macaroon cake jelly sweet. Sweet chocolate bar shortbread chocolate muffin. Brownie chocolate bar cupcake chupa chups cupcake cookie. Jelly icing danish macaroon chupa chups lemon drops dragée sesame snaps jujubes.",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        brush = Brush.verticalGradient(
                            colors = gradientColors,
                        ),
                    ),
                )
            }
        }
    }
}

@Preview(widthDp = 420)
@Composable
private fun RecipeItemPreview() {
    CookbookTheme {
        RecipeItem()
    }
}

@Preview(widthDp = 420)
@Composable
private fun RecipeItemPlaceholderPreview() {
    CookbookTheme {
        RecipeItemPlaceholder()
    }
}
