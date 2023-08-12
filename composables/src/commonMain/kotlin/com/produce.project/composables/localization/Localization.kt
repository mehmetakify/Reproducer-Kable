package com.produce.project.composables.localization

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Localization<T>(
    private val defaultLanguageTag: LanguageTag,
    private val translations: Map<LanguageTag, T>
) {

    var languageTag: LanguageTag by mutableStateOf(defaultLanguageTag)

    val strings: T
        get() = translations[languageTag]
            ?: translations[languageTag.fallback]
            ?: translations[defaultLanguageTag]
            ?: error("AppStrings for language tag $languageTag not found")

    private val LanguageTag.fallback: LanguageTag
        get() = split(LANGUAGE_TAG_SEPARATOR).first()

    companion object {
        private const val LANGUAGE_TAG_SEPARATOR = '-'
    }
}
