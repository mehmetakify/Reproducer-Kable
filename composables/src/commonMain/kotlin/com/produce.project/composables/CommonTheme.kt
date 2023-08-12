package com.produce.project.composables

import androidx.compose.runtime.Composable
import com.produce.project.composables.localization.AppStrings
import com.produce.project.composables.localization.Localization
import com.produce.project.composables.localization.ProvideStrings
import com.produce.project.composables.localization.rememberStrings
import com.produce.project.composables.styling.Produce
import com.produce.project.mobile.viewmodel.DKMPViewModel

lateinit var localization: Localization<AppStrings>

@Composable
fun CommonTheme(
    model: DKMPViewModel
){
    localization = rememberStrings()
    localization.languageTag = model.navigation.getLanguage()
    ProvideStrings(localization) {
        Produce { MainComposable(model) }
    }
}