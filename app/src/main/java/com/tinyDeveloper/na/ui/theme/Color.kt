package com.tinyDeveloper.na.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.tinyDeveloper.na.utils.Constants.HALF_ALPHA

val md_theme_light_primary = Color(0xFF1660A5)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFD3E4FF)
val md_theme_light_onPrimaryContainer = Color(0xFF001C38)
val md_theme_light_secondary = Color(0xFF006C53)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFF98E1F3)
val md_theme_light_onSecondaryContainer = Color(0xFF002117)
val md_theme_light_tertiary = Color(0xFF78CFF3)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFC8E6FF)
val md_theme_light_onTertiaryContainer = Color(0xFF001E2E)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFBFCFF)
val md_theme_light_onBackground = Color(0xFF001E2D)
val md_theme_light_surface = Color(0xFFFBFCFF)
val md_theme_light_onSurface = Color(0xFF001E2D)
val md_theme_light_surfaceVariant = Color(0xFFDFE2EB)
val md_theme_light_onSurfaceVariant = Color(0xFF43474E)
val md_theme_light_outline = Color(0xFF73777F)
val md_theme_light_inverseOnSurface = Color(0xFFE4F3FF)
val md_theme_light_inverseSurface = Color(0xFF00344B)
val md_theme_light_inversePrimary = Color(0xFFA3C9FF)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF1660A5)
val md_theme_light_outlineVariant = Color(0xFFC3C6CF)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFA3C9FF)
val md_theme_dark_onPrimary = Color(0xFF00315C)
val md_theme_dark_primaryContainer = Color(0xFF004882)
val md_theme_dark_onPrimaryContainer = Color(0xFFD3E4FF)
val md_theme_dark_secondary = Color(0xFF2FA5DD)
val md_theme_dark_onSecondary = Color(0xFF00382A)
val md_theme_dark_secondaryContainer = Color(0xFF145569)
val md_theme_dark_onSecondaryContainer = Color(0xFF6CFACD)
val md_theme_dark_tertiary = Color(0xFF87CEFF)
val md_theme_dark_onTertiary = Color(0xFF00344D)
val md_theme_dark_tertiaryContainer = Color(0xFF004C6D)
val md_theme_dark_onTertiaryContainer = Color(0xFFC8E6FF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF001E2D)
val md_theme_dark_onBackground = Color(0xFFC6E7FF)
val md_theme_dark_surface = Color(0xFF001E2D)
val md_theme_dark_onSurface = Color(0xFFC6E7FF)
val md_theme_dark_surfaceVariant = Color(0xFF43474E)
val md_theme_dark_onSurfaceVariant = Color(0xFFC3C6CF)
val md_theme_dark_outline = Color(0xFF8D9199)
val md_theme_dark_inverseOnSurface = Color(0xFF001E2D)
val md_theme_dark_inverseSurface = Color(0xFFC6E7FF)
val md_theme_dark_inversePrimary = Color(0xFF1660A5)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFA3C9FF)
val md_theme_dark_outlineVariant = Color(0xFF43474E)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFF005496)


val LowPriorityColor = Color(0xFF00C980)
val AcceptablePriorityColor = Color(0xFF6FB662)
val MediumPriorityColor = Color(0xFFCF9900)
val WaitingPriorityColor = Color(0xFF968760)
val HidePriorityColor = Color(0xFFB9A777)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = Color(0xFF8F8F8F)

val BorderColor @Composable get() =
    if (isSystemInDarkTheme()) Color.White.copy(alpha = HALF_ALPHA)
    else Color.Black.copy(alpha = HALF_ALPHA)

val TextColor @Composable get() =
    if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.70f)
    else Color.Black.copy(alpha = HALF_ALPHA)

val CardColor @Composable get() =
    if (isSystemInDarkTheme()) Color(0xFF2B3D3C) else Color(0xFFDDEEEE)

val DropDownColor @Composable get() =
    if (isSystemInDarkTheme()) Color.White.copy(alpha = HALF_ALPHA) else Color.Black.copy(alpha = HALF_ALPHA)

val NumberPadBackButton @Composable get() =
    if (isSystemInDarkTheme()) Color(0xFFC7A28E) else Color(0xFFAD6F39)

val TopBarDivider @Composable get() =
    if (isSystemInDarkTheme()) Color(0x00FFFFFF) else Color.LightGray.copy(alpha = 0.5f)

val ShimmerColor @Composable get() =
    if (isSystemInDarkTheme()) Color.DarkGray.copy(HALF_ALPHA) else Color.LightGray

val NotShimmerColor @Composable get() =
    if (isSystemInDarkTheme()) Color.LightGray.copy(HALF_ALPHA) else Color.DarkGray.copy(HALF_ALPHA)