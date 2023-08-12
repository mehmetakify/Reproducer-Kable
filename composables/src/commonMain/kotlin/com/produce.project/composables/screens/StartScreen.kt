package com.produce.project.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.produce.project.composables.localization.LocalStrings
import com.produce.project.composables.styling.*
import com.produce.project.mobile.viewmodel.screens.start.StartState


@Composable
fun StartState.LoginScreen(
) {

    val strings = LocalStrings.current

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = strings.appName,
            style = MaterialTheme.typography.h3,
            color = textColor,
            textAlign = TextAlign.Start
        )
    }
}