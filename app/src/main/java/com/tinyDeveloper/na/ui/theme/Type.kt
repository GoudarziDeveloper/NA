package com.tinyDeveloper.na.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tinyDeveloper.na.R

// Set of Material typography styles to start with
val erenaFontFamily = FontFamily(
    Font(R.font.ya_medium, FontWeight.Medium),
    Font(R.font.ya_bold, FontWeight.Bold),
    Font(R.font.ya_reqular, FontWeight.Normal),
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = erenaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = erenaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = erenaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = erenaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = erenaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.3.sp
    ),
    displaySmall = TextStyle(
        fontFamily = erenaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)