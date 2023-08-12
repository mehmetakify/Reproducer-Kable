package com.produce.project.composables.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale

typealias LanguageTag = String

@Composable
fun <T> rememberStrings(
    translations: Map<LanguageTag, T>,
    languageTag: LanguageTag = Locale.current.toLanguageTag()
): Localization<T> =
    remember { Localization(languageTag, translations) }

@Composable
fun <T> ProvideStrings(
    localization: Localization<T>,
    provider: ProvidableCompositionLocal<T>,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        provider provides localization.strings,
        content = content
    )
}
