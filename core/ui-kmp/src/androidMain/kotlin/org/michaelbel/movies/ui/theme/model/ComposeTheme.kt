@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.ui.theme.model

import androidx.compose.material3.ColorScheme

actual data class ComposeTheme(
    val colorScheme: ColorScheme,
    val detectDarkMode: Boolean
)