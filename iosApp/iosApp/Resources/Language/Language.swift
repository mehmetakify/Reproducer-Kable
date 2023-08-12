//
//  Language.swift
//  iosApp
//
//  Created by ramazankani on 10.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

enum Language: String {

    case english_us = "en"
    case russian = "ru"
    case spanish = "es"
    case turkish = "tr"
    
    var userSymbol: String {
        switch self {
        case .english_us:
            return "us"
        default:
            return rawValue
        }
    }
}
