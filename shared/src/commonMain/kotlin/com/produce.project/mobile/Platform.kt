package com.produce.project.mobile

import com.juul.kable.Scanner
interface Platform {
    val name: String
    val scanner: Scanner?
}

expect fun getPlatform(): Platform