package com.ix.cookbook.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ix.cookbook.R

/*
M3	Default Font Size/Line Height

displayLarge	Roboto              57/64
displayMedium	Roboto              45/52
displaySmall	Roboto              36/44
headlineLarge	Roboto              32/40
headlineMedium	Roboto              28/36
headlineSmall	Roboto              24/32
titleLarge	    New- Roboto Medium  22/28
titleMedium	    Roboto Medium       16/24
titleSmall	    Roboto Medium       14/20
bodyLarge	    Roboto              16/24
bodyMedium	    Roboto              14/20
bodySmall	    Roboto              12/16
labelLarge	    Roboto Medium       14/20
labelMedium	    Roboto Medium       12/16
labelSmall	    New Roboto Medium,  11/16
* */

object Fonts {
    val title = FontFamily(Font(R.font.indieflower_regular))
    val body = FontFamily(Font(R.font.notosans_regular))
}

// Set of Material typography styles to start with
val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = Fonts.body,
        fontSize = 16.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Fonts.body,
        fontSize = 14.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Fonts.title,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 26.sp,
//        letterSpacing = 0.15.sp,
    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
