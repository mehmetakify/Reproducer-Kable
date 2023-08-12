package com.produce.project.mobile.viewmodel

import com.produce.project.mobile.viewmodel.screens.*
import com.produce.project.mobile.viewmodel.utils.Util
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.runBlocking

class Navigation(val stateManager: StateManager) {

    init {
        navigateByScreenIdentifier(navigationSettings.startScreen.screenIdentifier)
    }

    val stateProvider by lazy { StateProvider(stateManager) }
    val events by lazy { Events(stateManager) }

    fun getTitle(screenIdentifier: ScreenIdentifier): String {
        return screenIdentifier.getScreenInitSettings(this).title
    }

    val dataRepository
        get() = stateManager.dataRepository

    val currentScreenIdentifier: ScreenIdentifier
        get() = stateManager.currentScreenIdentifier

    val currentLevel1ScreenIdentifier: ScreenIdentifier
        get() = stateManager.currentLevel1ScreenIdentifier

//    val only1ScreenInBackstack : Boolean
//        get() = stateManager.only1ScreenInBackstack

    // used by the Router composable in Compose apps
    // it returns a list of screens whose state has been removed, so they should also be removed from Compose's SaveableStateHolder
    val screenStatesToRemove: List<ScreenIdentifier>
        get() = stateManager.getScreenStatesToRemove()

    // used by the Router view in SwiftUI apps
    // it returns the list of Level1 screens to be rendered inside a SwiftUI's ZStack
    val level1ScreenIdentifiers: List<ScreenIdentifier>
        get() = stateManager.getLevel1ScreenIdentifiers()

    fun getNavigationLevelsMap(level1ScreenIdentifier: ScreenIdentifier): Map<Int, ScreenIdentifier>? {
        return stateManager.verticalNavigationLevels[level1ScreenIdentifier.URI]
    }

    fun isInCurrentVerticalBackstack(screenIdentifier: ScreenIdentifier): Boolean {
//        if (screenIdentifier.screen.asString == "bluetooth") {
//            return true
//        }

        stateManager.currentVerticalBackstack.forEach {
            if (it.URI == screenIdentifier.URI) {
                return true
            }
        }
        return false
    }


    fun navigate(screen: Screen) {
        navigateByScreenIdentifier(ScreenIdentifier.get(screen))
    }

    fun navigateByLevel1Menu(level1NavigationItem: Level1Navigation) {
        val navigationLevelsMap = getNavigationLevelsMap(level1NavigationItem.screenIdentifier)
        if (level1NavigationItem.name == navigationSettings.startScreen.name) {
            stateManager.level1Backstack.removeAll(stateManager.level1Backstack)
            stateManager.level1Backstack.add(navigationSettings.startScreen.screenIdentifier)
        } else if (level1NavigationItem.name == navigationSettings.homeScreen.name) {
            exitScreen()
        }
        if (navigationLevelsMap == null) {
            navigateByScreenIdentifier(level1NavigationItem.screenIdentifier)
        } else {
            navigationLevelsMap.keys.sorted().forEach {
                navigateByScreenIdentifier(navigationLevelsMap[it]!!)
            }
        }

//        val navigationLevelsMap = getNavigationLevelsMap(level1NavigationItem.screenIdentifier)
//        if (navigationLevelsMap==null) {
//            navigateByScreenIdentifier(level1NavigationItem.screenIdentifier)
//        } else {
//            navigationLevelsMap.keys.sorted().forEach {
//                navigateByScreenIdentifier(navigationLevelsMap[it]!!)
//            }
//        }
    }

    fun navigateByScreenIdentifier(screenIdentifier: ScreenIdentifier) {
//        println(screenIdentifier.screen.asString)
//        if (screenIdentifier.screen.asString == "bluetooth") {
//
//        } else {
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        stateManager.addScreen(screenIdentifier, screenInitSettings)
//        }

//        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
//        stateManager.addScreen(screenIdentifier, screenInitSettings)
//        if (navigationSettings.saveLastLevel1Screen && screenIdentifier.screen.navigationLevel == 1) {
//            dataRepository.localSettings.savedLevel1URI = screenIdentifier.URI
//        }
    }

    fun exitScreen(
        screenIdentifier: ScreenIdentifier? = null,
        triggerRecomposition: Boolean = true
    ) {
        val sID = screenIdentifier ?: currentScreenIdentifier
        if (stateManager.level1Backstack.size > 1 || (stateManager.level1Backstack.size == 1 && currentScreenIdentifier.screen.navigationLevel > 1)) {
            stateManager.removeScreen(sID)
            if (triggerRecomposition) navigateByScreenIdentifier(currentScreenIdentifier)
        }

//        val sID = screenIdentifier ?: currentScreenIdentifier
//        debugLogger.log("exitScreen: "+sID.URI)
//        stateManager.removeScreen(sID)
//        if (triggerRecomposition) {
//            navigateByScreenIdentifier(currentScreenIdentifier)
//        }
    }

    private fun navigateLoginScreen() {
        navigateByLevel1Menu(navigationSettings.startScreen)
    }

    fun getLanguage(): String = dataRepository.getStartData()?.l ?: "en"

    fun onReEnterForeground(isComposable: Boolean) {
        // not called at app startup, but only when reentering the app after it was in background
        if (currentScreenIdentifier.URI != navigationSettings.startScreen.screenIdentifier.URI) {
            runBlocking {
                val backgroundTime = dataRepository.getStartData()?.b
                if (backgroundTime != null) {
                    if (Util.getTimestamp(RealmInstant.now()) - backgroundTime > 60000L) {
                        dataRepository.removeBackgroundTime()
                        navigateLoginScreen()
                    } else if (!isComposable) {
                        dataRepository.setDefaultIsBackground()
                    }
                } else {
                    navigateLoginScreen()
                }
            }
        }

        if (isComposable) {
            val reinitializedScreens = stateManager.reinitScreenScopes()
            stateManager.triggerRecomposition()
            reinitializedScreens.forEach {
                it.getScreenInitSettings(this).apply {
                    if (callOnInitAlsoAfterBackground) {
                        stateManager.runInScreenScope { callOnInit(stateManager) }
                    }
                }
            }
        }
    }

    fun onEnterBackground(isComposable: Boolean, isBackground: Boolean = false) {
        runBlocking {
            if (currentScreenIdentifier.URI == navigationSettings.startScreen.screenIdentifier.URI) {
                dataRepository.removeBackgroundTime()
            } else if (isComposable) {
                dataRepository.saveBackgroundTime(isBackground)
            } else if (dataRepository.getStartData()?.z != true) {
                dataRepository.saveBackgroundTime(isBackground)
            }
        }
        if (isComposable) stateManager.cancelScreenScopes()
    }

    fun onChangeOrientation() {
        stateManager.triggerRecomposition()
    }


}