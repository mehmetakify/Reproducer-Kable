//
//  MainView.swift
//  iosApp
//
//  Created by ramazankani on 16.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared
import PopupView

struct MainView: View {
    @EnvironmentObject var appObj: AppObservableObject
    
    var body: some View {
        ZStack {
            let dkmpNav = appObj.dkmpNav
            dkmpNav.router()
        }
    }
}
