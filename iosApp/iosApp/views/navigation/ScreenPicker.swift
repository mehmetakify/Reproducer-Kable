//
//  ScreenPicker.swift
//  iosApp
//
//  Created by ramazankani on 21.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

import SwiftUI
import shared

extension Navigation {
    
    @ViewBuilder func screenPicker(_ sId: ScreenIdentifier) -> some View {
        
        VStack {
            switch sId.screen {
                
            case .start:
                StartScreen()
            default:
                EmptyView()
            }
        }
    }
}


