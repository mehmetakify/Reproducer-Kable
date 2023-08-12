//
//  StartScreen.swift
//  iosApp
//
//  Created by user on 8/10/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

import SwiftUI

struct StartScreen: View {
    var body: some View {
        GeometryReader { geo in
            VStack {
                Spacer()
                Text("Default")
                Spacer()
            }
            .frame(width: geo.size.width)
        }
        .edgesIgnoringSafeArea(.all)
    }
}
