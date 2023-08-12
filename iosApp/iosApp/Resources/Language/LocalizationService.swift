//
//  LocalizationService.swift
//  iosApp
//
//  Created by ramazankani on 10.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class LocalizationService {

    static let shared = LocalizationService()
//    static let changedLanguage = Notification.Name("changedLanguage")

    private init() {}
    
    var language: Language {
        get {
//            guard let languageString = UserDefaults.standard.string(forKey: "language") else {
//                return Language(rawValue: Locale.current.languageCode ?? "us") ?? .english_us
//            }
//            return Language(rawValue: languageString) ?? .english_us
            return .english_us
        } set {
            if newValue != language {
                UserDefaults.standard.setValue(newValue.rawValue, forKey: "language")
//                NotificationCenter.default.post(name: LocalizationService.changedLanguage, object: nil)
            }
        }
    }
}
