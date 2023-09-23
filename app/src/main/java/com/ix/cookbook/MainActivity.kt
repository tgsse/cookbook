package com.ix.cookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ix.cookbook.navigation.NavigationHost
import com.ix.cookbook.ui.theme.CookBookTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookBookTheme {
                NavigationHost()
            }
        }
    }
}
