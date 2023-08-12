//
//  AppObservableObject.swift
//  iosApp
//
//  Created by ramazankani on 16.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

class AppObservableObject: ObservableObject {
    let model : DKMPViewModel = DKMPViewModel.Factory().getIosInstance()

    var dkmpNav : Navigation {
        return self.appState.getNavigation(model: self.model)
    }

    @Published var appState : AppState
    
    init() {
        // "getDefaultAppState" and "onChange" are iOS-only DKMPViewModel's extension functions, defined in shared/iosMain
        self.appState = model.getDefaultAppState()

        model.onChange { newState in
            self.appState = newState
        }
    }
}
