//
//  LoadingScreen.swift
//  iosApp
//
//  Created by ramazankani on 21.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

import SwiftUI

struct LoadingScreen: View {
    var body: some View {
        GeometryReader { geo in
            VStack {
                Spacer()
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle())
                    .accentColor(Color.white)
                    .scaleEffect(x: 2, y: 2, anchor: .center)
                Spacer()
            }
            .frame(width: geo.size.width)
        }
        .background(Color.black.opacity(0.2))
        .edgesIgnoringSafeArea(.all)
    }
}
