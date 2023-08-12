//
//  Router.swift
//  iosApp
//
//  Created by ramazankani on 21.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

let twopaneWidthThreshold : CGFloat = 1000

extension Navigation {
    
    @ViewBuilder func router() -> some View {
        self.onePane(self.currentScreenIdentifier)
    }
    
    func navigate(_ screen: Screen) -> ScreenIdentifier {
        return ScreenIdentifier.Factory().get(screen: screen)
    }
}

struct CustomNavigationLink<Content: View>: View {
    var linkFunction: () -> ScreenIdentifier
    let content: () -> Content
    
    @EnvironmentObject var appObj: AppObservableObject
    @State private var selected : Bool = false
    
    var body: some View {
        let isActive = Binding<Bool> (
            get: {
                selected && appObj.dkmpNav.isInCurrentVerticalBackstack(screenIdentifier: linkFunction())
            },
            set: { isActive in
                if isActive {
                    let screenIdentifier = linkFunction()
                    appObj.dkmpNav.navigateByScreenIdentifier(screenIdentifier: screenIdentifier)
                    self.selected = true
                }
            }
        )
        
        NavigationLink(
            destination: CustomLazyDestination(
                appObj.dkmpNav.screenPicker(linkFunction())
                //                    .navigationBarTitle(appObj.dkmpNav.getTitle(screenIdentifier: linkFunction()), displayMode: .inline)
                    .onDisappear {
                        let screenIdentifier = linkFunction()
                        
                        print("back button pressed => " + screenIdentifier.screen.asString)
                        print("onDisappear: "+screenIdentifier.URI)
                        
                        if appObj.dkmpNav.isInCurrentVerticalBackstack(screenIdentifier: screenIdentifier) {
                            print("confimed disappear")
                            self.selected = false
                            isActive.wrappedValue = false
                            appObj.dkmpNav.exitScreen(screenIdentifier: screenIdentifier, triggerRecomposition: false)
                        }
                    }
            ),
            isActive: isActive
        ) {
            content()
        }
    }
}

struct CustomLazyDestination<Content: View>: View {
    let build: () -> Content
    init(_ build: @autoclosure @escaping () -> Content) {
        self.build = build
    }
    var body: Content {
        build()
    }
}

struct NavLink<Content: View>: View {
    var linkFunction: () -> ScreenIdentifier
    let content: () -> Content
    
    @EnvironmentObject var appObj: AppObservableObject
    @State private var selected : Bool = true
    var body: some View {
        
        let isActive = Binding<Bool> (
            get: {
                selected && appObj.dkmpNav.isInCurrentVerticalBackstack(screenIdentifier: linkFunction())
            },
            set: { isActive in
                if isActive {
                    let screenIdentifier = linkFunction()
                    print("navigate to  => " + screenIdentifier.screen.asString)
                    appObj.dkmpNav.navigateByScreenIdentifier(screenIdentifier: screenIdentifier)
                    self.selected = true
                }
            }
        )
        
        NavigationLink(
            destination: LazyDestinationView(
                appObj.dkmpNav.screenPicker(linkFunction())
                    .navigationBarHidden(true)
//                    .navigationBarTitle(appObj.dkmpNav.getTitle(screenIdentifier: linkFunction()), displayMode: .inline)
                    .onDisappear {
                        let screenIdentifier = linkFunction()
                        //print("onDisappear: "+screenIdentifier.URI)
                        if appObj.dkmpNav.isInCurrentVerticalBackstack(screenIdentifier: screenIdentifier) {
                            print("confimed disappear")
                            self.selected = false
                            isActive.wrappedValue = false
                            appObj.dkmpNav.exitScreen(screenIdentifier: screenIdentifier, triggerRecomposition: false)
                        }
                    }
            ),
            isActive: isActive
        ) {
            content()
        }
    }
}

struct LazyDestinationView<Content: View>: View {
    let build: () -> Content
    init(_ build: @autoclosure @escaping () -> Content) {
        self.build = build
    }
    var body: Content {
        build()
    }
}
