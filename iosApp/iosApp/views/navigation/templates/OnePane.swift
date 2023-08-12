//
//  OnePane.swift
//  iosApp
//
//  Created by ramazankani on 21.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

import SwiftUI
import shared

extension Navigation {
    
    @ViewBuilder func onePane(_ level1ScreenIdentifier: ScreenIdentifier) -> some View {
        
        if level1ScreenIdentifier.screen.navigationLevel == 0 && level1ScreenIdentifier.URI == self.currentLevel1ScreenIdentifier.URI {
            NavigationView {
                ZStack {
                    VStack(spacing: 0) {
                        self.screenPicker(level1ScreenIdentifier)
                    }
                }
                .navigationBarHidden(true)
            }
            .navigationViewStyle(StackNavigationViewStyle())
        }
    }
}
