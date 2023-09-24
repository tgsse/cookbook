package com.ix.cookbook

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.ix.cookbook.navigation.NavigationHost
import com.ix.cookbook.navigation.routes
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationHost(navController)
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.content_desc_recipes_screen))
            .assertIsDisplayed()
    }

    @Test
    fun appNavHost_clickAllTabs_navigateToTabs() {
        with(composeTestRule) {
            routes.reversed().forEach { screen ->
                onNodeWithText(context.getString(screen.label))
                    .assertIsDisplayed()
                    .assertHasClickAction()
                    .performClick()

                val route = navController.currentBackStackEntry?.destination?.route
                assertEquals(route, screen.route)
            }
        }
    }
}