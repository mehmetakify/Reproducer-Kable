//
//  Level1BottomBar.swift
//  iosApp
//
//  Created by ramazankani on 21.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

import SwiftUI
import shared


extension Navigation {
    
    // this is the bottom horizontal navigation bar for 1-Pane visualization
    // (used by small devices and in Portrait mode)
    
    @ViewBuilder func level1ButtonBar(selectedTab: ScreenIdentifier) -> some View {
        
        HStack {
            Spacer()
            BottomBarButton(
                iconName: "",
                selected: selectedTab.URI==Level1Navigation.start.screenIdentifier.URI,
                height: 20,
                onClick: { self.navigateByLevel1Menu(level1NavigationItem: Level1Navigation.start) }
            )
            Spacer()
            BottomBarButton(
                iconName: "",
                selected: selectedTab.URI==Level1Navigation.start.screenIdentifier.URI,
                height: 20,
                onClick: { self.navigateByLevel1Menu(level1NavigationItem: Level1Navigation.start) }
            )
            Spacer()
            BottomBarButton(
                iconName: "",
                selected: selectedTab.URI==Level1Navigation.start.screenIdentifier.URI,
                height: 20,
                onClick: { self.navigateByLevel1Menu(level1NavigationItem: Level1Navigation.start) }
            )
            Spacer()
        }
        .frame(height: 50, alignment: .center)
        .background(.white)
        .cornerRadius(5)
        .overlay(RoundedRectangle(cornerRadius: 5).stroke(.white, lineWidth: 0.4))
        .shadow(radius: 2, y: 4)
        .padding(.horizontal, 25)
        .padding(.bottom, 16)
    }
}

struct BottomBarButton: View {
    var iconName : String
    var selected : Bool
    var height : CGFloat = 30
    var onClick : () -> Void
    
    var body: some View {
        Button(action: { onClick() }) {
            Image(iconName)
                .resizable()
                .scaledToFit()
                .frame(height:height)
                .blending(color: selected ? .gray : .black)
        }
    }
}

extension View {
    public func blending(color: Color) -> some View {
        modifier(ColorBlended(color: color))
    }
}

public struct ColorBlended: ViewModifier {
    fileprivate var color: Color
    
    public func body(content: Content) -> some View {
        VStack {
            ZStack {
                content
                color.blendMode(.sourceAtop)
            }
            .drawingGroup(opaque: false)
        }
    }
}
