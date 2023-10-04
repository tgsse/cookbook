package com.ix.cookbook

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.ix.cookbook.screens.recipes.RecipesScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecipesMainTabsTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        composeTestRule.setContent {
            RecipesScreen({})
        }
    }

    @Test
    fun noRecipes_showCorrectMessage() {
        composeTestRule
            .onNodeWithText(context.getString(R.string.error_recipes_not_found))
            .assertIsDisplayed()
    }
}
