package com.produce.project.composables.navigation.templates

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.produce.project.composables.navigation.ScreenPicker
import com.produce.project.mobile.viewmodel.DKMPViewModel
import com.produce.project.mobile.viewmodel.Navigation
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.produce.project.composables.styling.*
import com.produce.project.composables.styling.iconhelpers.Icon
import kotlinx.coroutines.delay

@Composable
fun Navigation.OnePane(
    saveableStateHolder: SaveableStateHolder,
    model: DKMPViewModel
) {

    val isLoggedIn = currentScreenIdentifier.screen.navigationLevel != 0

    val animatedProgress by animateFloatAsState(
        targetValue = (if (model.stateFlow.value.showNotification) 0f else -350f),
        animationSpec = tween(500)
    )
    val openLangModal = remember { mutableStateOf(false) }

    Box(
        Modifier.zIndex(1f).absoluteOffset(animatedProgress.dp, 50.dp)
            .background(if (model.stateFlow.value.isErrorNotif) Color(0xFFD63333) else notifBack)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                if (model.stateFlow.value.isErrorNotif) IcRejected else IcTick,
                "Success",
                Modifier.padding(10.dp),
                if (model.stateFlow.value.isErrorNotif) Color.Unspecified else Color(0xFF42905C)
            )
        }
    }

    if (model.stateFlow.value.showNotification) {
        LaunchedEffect(Unit) {
            val notificationType = model.stateFlow.value.notificationType
            val isErrorNotif = model.stateFlow.value.isErrorNotif
            delay(3000L)
            events.stateManager.showNotification(
                false,
                notificationType,
                isErrorNotif
            )

            delay(600L)
            events.stateManager.showNotification(false, 0, false)
        }
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        LaunchedEffect(Unit) {
            stateManager.setScreenSize(maxWidth.value.toInt(), maxHeight.value.toInt())
        }
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                if (isLoggedIn) {
                    if (model.stateFlow.value.openModal) {
                        BoxWithConstraints(
                            modifier = Modifier.fillMaxWidth().height(123.dp).background(
                                disableBackColor
                            ).zIndex(1f),
                            contentAlignment = Alignment.Center
                        ) {
                        }
                    }
                    if (getTitle(currentScreenIdentifier) == "3") {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(17.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                IcLanguage,
                                "Language",
                                Modifier
                                    .clickable { openLangModal.value = true }
                                    .padding(5.dp)
                                    .clip(shape = CircleShape)
                            )
                        }
                    }
                }
            },
            content = {
                saveableStateHolder.SaveableStateProvider(currentScreenIdentifier.URI) {
                    ScreenPicker(
                        currentScreenIdentifier
                    )
                }
            }
        )
    }
}