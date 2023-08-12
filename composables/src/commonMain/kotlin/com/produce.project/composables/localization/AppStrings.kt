package com.produce.project.composables.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.intl.Locale

data class AppStrings(
    val appName: String
)

object Locales {
    const val Tr = "tr"
    const val En = "en"
}

val strings = mapOf(Locales.Tr to TrStrings, Locales.En to EnStrings)

val langList = strings.keys.toTypedArray()

val LocalStrings = staticCompositionLocalOf { EnStrings }

@Composable
fun rememberStrings(
    languageTag: LanguageTag = Locale.current.toLanguageTag(),
): Localization<AppStrings> = rememberStrings(strings, languageTag)

@Composable
fun ProvideStrings(
    localization: Localization<AppStrings>,
    content: @Composable () -> Unit
) {
    ProvideStrings(localization, LocalStrings, content)
}