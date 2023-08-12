//
//  iOSApp.swift
//  iosApp
//
//  Created by ramazankani on 21.10.2022.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

@main
struct iosApp: App {
    @StateObject var appObj = AppObservableObject()
    
    @Environment(\.scenePhase) var scenePhase
    
    var body: some Scene {
        WindowGroup {
            MainView()
                .environmentObject(appObj)
                .onChange(of: scenePhase) { newPhase in
                    if newPhase == .active {
                        appObj.dkmpNav.onReEnterForeground(isComposable: false)
                    }
                    else if newPhase == .inactive {
                        appObj.dkmpNav.onEnterBackground(isComposable: false, isBackground: false)
                    }
                    else if newPhase == .background {
                        appObj.dkmpNav.onEnterBackground(isComposable: false, isBackground: true)
                    }
                }
                .onReceive(NotificationCenter.default.publisher(for: UIDevice.orientationDidChangeNotification)) { _ in
                    appObj.dkmpNav.onChangeOrientation()
                }
        }
    }
}
