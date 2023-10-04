package com.ix.cookbook.navigation.util

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

open class NavigationTab(
    val route: String,
    @StringRes val label: Int,
)

class NavigationTabWithIcon(
    route: String,
    @StringRes label: Int,
    val icon: ImageVector,
) : NavigationTab(route, label)
