package com.produce.project.mobile.viewmodel

import com.produce.project.mobile.datalayer.Repository
import com.produce.project.mobile.datalayer.objects.Lock
import com.produce.project.mobile.viewmodel.screens.ScreenInitSettings
import com.produce.project.mobile.viewmodel.screens.navigationSettings
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KClass


interface ScreenState

class StateManager(repo: Repository) {

    internal val mutableStateFlow = MutableStateFlow(AppState())

    val screenStatesMap: MutableMap<URI, ScreenState> =
        mutableMapOf() // map of screen states currently in memory
    val screenScopesMap: MutableMap<URI, CoroutineScope> =
        mutableMapOf() // map of coroutine scopes associated to current screen states

    val level1Backstack: MutableList<ScreenIdentifier> =
        mutableListOf() // list elements are only NavigationLevel1 screenIdentifiers
    val verticalBackstacks: MutableMap<URI, MutableList<ScreenIdentifier>> =
        mutableMapOf() // the map keys is NavigationLevel1 screenIdentifier URI
    val verticalNavigationLevels: MutableMap<URI, MutableMap<Int, ScreenIdentifier>> =
        mutableMapOf() // the first map key is the NavigationLevel1 screenIdentifier URI, the second map key is the NavigationLevel numbers

    val lastRemovedScreens = mutableListOf<ScreenIdentifier>()

    internal val dataRepository by lazy { repo }

    val currentScreenIdentifier: ScreenIdentifier
        get() = currentVerticalBackstack.lastOrNull() ?: level1Backstack.last()
    val currentLevel1ScreenIdentifier: ScreenIdentifier
        get() = level1Backstack.last()
    val currentVerticalBackstack: MutableList<ScreenIdentifier>
        get() = verticalBackstacks[currentLevel1ScreenIdentifier.URI]!!
    val currentVerticalNavigationLevelsMap: MutableMap<Int, ScreenIdentifier>
        get() = verticalNavigationLevels[currentLevel1ScreenIdentifier.URI]!!


    // used by Compose apps
    fun getScreenStatesToRemove(): List<ScreenIdentifier> {
        val screenStatesToRemove = lastRemovedScreens.toList()
        lastRemovedScreens.clear()
        return screenStatesToRemove
    }

    // used by SwiftUI apps
    fun getLevel1ScreenIdentifiers(): List<ScreenIdentifier> {
        val screenIdentifiers = verticalNavigationLevels.values.map { it[1]!! }.toMutableList()
        screenIdentifiers.removeAll { !screenStatesMap.containsKey(it.URI) }  // remove all that don't have the state stored
        return screenIdentifiers
    }

    fun isInTheStatesMap(screenIdentifier: ScreenIdentifier): Boolean {
        return screenStatesMap.containsKey(screenIdentifier.URI)
    }

    fun isInAnyVerticalBackstack(screenIdentifier: ScreenIdentifier): Boolean {
        verticalBackstacks.forEach { verticalBackstack ->
            verticalBackstack.value.forEach {
                if (it.URI == screenIdentifier.URI) {
                    return true
                }
            }
        }
        return false
    }

    inline fun <reified T : ScreenState> updateScreen(
        stateClass: KClass<T>,
        update: (T) -> T,
    ) {

        lateinit var screenIdentifier: ScreenIdentifier
        var screenState: T? = null
        for (i in currentVerticalNavigationLevelsMap.keys.sortedDescending()) {
            screenState = screenStatesMap[currentVerticalNavigationLevelsMap[i]?.URI] as? T
            if (screenState != null) {
                screenIdentifier = currentVerticalNavigationLevelsMap[i]!!
                break
            }
        }
        if (screenState != null) { // only perform screen state update if screen is currently visible
            screenStatesMap[screenIdentifier.URI] = update(screenState)
            triggerRecomposition()
        }
    }


    fun getScreenSize() = Pair(mutableStateFlow.value.width, mutableStateFlow.value.height)
    fun setScreenSize(width: Int, height: Int) {
        mutableStateFlow.value = AppState(
            mutableStateFlow.value.recompositionIndex,
            mutableStateFlow.value.isLoading,
            mutableStateFlow.value.showNotification,
            mutableStateFlow.value.notificationType,
            width,
            height,
            openModal = mutableStateFlow.value.openModal,
            isErrorNotif = mutableStateFlow.value.isErrorNotif
        )
    }


    fun triggerRecomposition() {
        mutableStateFlow.value = AppState(
            mutableStateFlow.value.recompositionIndex + 1,
            mutableStateFlow.value.isLoading,
            mutableStateFlow.value.showNotification,
            mutableStateFlow.value.notificationType,
            mutableStateFlow.value.width,
            mutableStateFlow.value.height,
            openModal = mutableStateFlow.value.openModal,
            isErrorNotif = mutableStateFlow.value.isErrorNotif
        )
    }

    fun openModal(value: Boolean) {
        if (mutableStateFlow.value.openModal != value)
            mutableStateFlow.value = AppState(
                mutableStateFlow.value.recompositionIndex,
                mutableStateFlow.value.isLoading,
                mutableStateFlow.value.showNotification,
                mutableStateFlow.value.notificationType,
                mutableStateFlow.value.width,
                mutableStateFlow.value.height,
                value,
                mutableStateFlow.value.isErrorNotif
            )
    }

    fun triggerLoading(isLoading: Boolean) {
        mutableStateFlow.value = AppState(
            mutableStateFlow.value.recompositionIndex,
            isLoading,
            mutableStateFlow.value.showNotification,
            mutableStateFlow.value.notificationType,
            mutableStateFlow.value.width,
            mutableStateFlow.value.height,
            openModal = mutableStateFlow.value.openModal,
            isErrorNotif = mutableStateFlow.value.isErrorNotif
        )
    }

    fun showNotification(showNotification: Boolean, notificationType: Int, isError: Boolean = false) {
        mutableStateFlow.value = AppState(
            mutableStateFlow.value.recompositionIndex,
            mutableStateFlow.value.isLoading,
            showNotification,
            notificationType,
            mutableStateFlow.value.width,
            mutableStateFlow.value.height,
            isErrorNotif = isError
        )
    }

    // ADD SCREEN FUNCTIONS

    fun addScreen(screenIdentifier: ScreenIdentifier, screenInitSettings: ScreenInitSettings) {
        addScreenToBackstack(screenIdentifier)
        initScreenScope(screenIdentifier)
        if (!isInTheStatesMap(screenIdentifier) || screenInitSettings.reinitOnEachNavigation) {

            if (screenInitSettings.initState(screenIdentifier) != null)
            screenStatesMap[screenIdentifier.URI] = screenInitSettings.initState(screenIdentifier)!!
            triggerRecomposition() // FIRST UI RECOMPOSITION
            runInScreenScope(screenIdentifier) {
                screenInitSettings.callOnInit(this) // SECOND UI RECOMPOSITION
            }
        } else {
            triggerRecomposition() // JUST 1 UI RECOMPOSITION
        }
    }

    private fun addScreenToBackstack(screenIdentifier: ScreenIdentifier) {
        if (screenIdentifier.screen.navigationLevel < 2) {
            if (level1Backstack.size > 0) {
                val sameAsNewScreen =
                    screenIdentifier.screen == currentLevel1ScreenIdentifier.screen
                clearLevel1Screen(currentLevel1ScreenIdentifier, sameAsNewScreen)
            }
            setupNewLevel1Screen(screenIdentifier)
        } else {
            if (currentScreenIdentifier.URI == screenIdentifier.URI) {
                return
            }
            if (currentScreenIdentifier.screen == screenIdentifier.screen && !screenIdentifier.screen.stackableInstances) {
                val currentScreenId = currentScreenIdentifier
                currentVerticalNavigationLevelsMap.remove(currentScreenId.screen.navigationLevel)
                currentVerticalBackstack.remove(currentScreenId)
                if (!isInAnyVerticalBackstack(currentScreenId)) {
                    removeScreenStateAndScope(currentScreenId)
                }
            }
        }

        if (currentVerticalBackstack.lastOrNull()?.URI != screenIdentifier.URI)
            currentVerticalBackstack.add(screenIdentifier)
        currentVerticalNavigationLevelsMap[screenIdentifier.screen.navigationLevel] =
            screenIdentifier
    }


    // REMOVE SCREEN FUNCTIONS

    fun removeScreen(screenIdentifier: ScreenIdentifier) {
        if (screenIdentifier.screen.navigationLevel < 2) {
            level1Backstack.remove(screenIdentifier)
            removeScreenStateAndScope(screenIdentifier)
        } else {
            currentVerticalNavigationLevelsMap.remove(screenIdentifier.screen.navigationLevel)
            currentVerticalBackstack.removeAll { it.URI == screenIdentifier.URI }
            currentVerticalNavigationLevelsMap[currentScreenIdentifier.screen.navigationLevel] =
                currentScreenIdentifier // set new currentScreenIdentifier, after the removal
            if (!isInAnyVerticalBackstack(screenIdentifier)) {
                removeScreenStateAndScope(screenIdentifier)
            }
        }
    }

    fun removeScreenStateAndScope(screenIdentifier: ScreenIdentifier) {
        screenScopesMap[screenIdentifier.URI]?.cancel() // cancel screen's coroutine scope
        screenScopesMap.remove(screenIdentifier.URI)
        screenStatesMap.remove(screenIdentifier.URI)
        lastRemovedScreens.add(screenIdentifier)
    }


    // LEVEL 1 NAVIGATION FUNCTIONS

    private fun clearLevel1Screen(screenIdentifier: ScreenIdentifier, sameAsNewScreen: Boolean) {
        if (!screenIdentifier.level1VerticalBackstackEnabled()) {
            currentVerticalBackstack.forEach {
                if (it.screen.navigationLevel > 1) {
                    removeScreenStateAndScope(it)
                }
            }
            currentVerticalBackstack.removeAll { it.URI != screenIdentifier.URI }
            currentVerticalNavigationLevelsMap.keys.removeAll { it != 1 }
        }
        if (sameAsNewScreen && !screenIdentifier.screen.stackableInstances) {
            removeScreenStateAndScope(screenIdentifier)
            currentVerticalBackstack.clear()
            currentVerticalNavigationLevelsMap.clear()
            level1Backstack.remove(screenIdentifier)
        }
    }

    fun setupNewLevel1Screen(screenIdentifier: ScreenIdentifier) {
        level1Backstack.removeAll { it.URI == screenIdentifier.URI }
        if (screenIdentifier.URI == navigationSettings.homeScreen.screenIdentifier.URI) {
            level1Backstack.clear() // remove all elements
        }
        addLevel1ScreenToBackstack(screenIdentifier)
    }

    private fun addLevel1ScreenToBackstack(screenIdentifier: ScreenIdentifier) {
        level1Backstack.add(screenIdentifier)
        if (verticalBackstacks[screenIdentifier.URI] == null) {
            verticalBackstacks[screenIdentifier.URI] = mutableListOf(screenIdentifier)
            verticalNavigationLevels[screenIdentifier.URI] = mutableMapOf(1 to screenIdentifier)
        }
    }


    // COROUTINE SCOPES FUNCTIONS

    private fun initScreenScope(screenIdentifier: ScreenIdentifier) {
        screenScopesMap[screenIdentifier.URI]?.cancel()
        screenScopesMap[screenIdentifier.URI] = CoroutineScope(Job() + Dispatchers.Main)
    }

    fun reinitScreenScopes(): List<ScreenIdentifier> {
        currentVerticalNavigationLevelsMap.forEach {
            screenScopesMap[it.value.URI] = CoroutineScope(Job() + Dispatchers.Main)
        }
        return currentVerticalNavigationLevelsMap.values.toMutableList() // return list of screens whose scope has been reinitialized
    }

    // we run each event function on a Dispatchers.Main coroutine
    fun runInScreenScope(screenIdentifier: ScreenIdentifier? = null, block: suspend () -> Unit) {
        val screenScope = screenScopesMap[screenIdentifier?.URI ?: currentScreenIdentifier.URI]
        screenScope?.launch {
            block()
        }
    }

    fun cancelScreenScopes() {
        screenScopesMap.forEach {
            it.value.cancel() // cancel screen's coroutine scope
        }
    }

}

data class AppState(
    val recompositionIndex: Int = 0,
    val isLoading: Boolean = false,
    val showNotification: Boolean = false,
    val notificationType: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val openModal: Boolean = false,
    val isErrorNotif: Boolean = false
) {
    fun getNavigation(model: DKMPViewModel): Navigation {
        return model.navigation
    }
}