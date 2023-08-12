package com.produce.project.mobile

import android.bluetooth.le.ScanSettings
import android.os.Build
import com.juul.kable.Filter
import com.juul.kable.ObsoleteKableApi
import com.juul.kable.Scanner
import com.juul.kable.logs.Logging
import com.juul.kable.logs.SystemLogEngine
import java.util.*

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT} - ${getDeviceName()}"
    @OptIn(ObsoleteKableApi::class)
    override val scanner = Scanner {
        scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .build()
        filters = listOf(Filter.NamePrefix("ZRN"))
        logging {
            engine = SystemLogEngine
            level = Logging.Level.Warnings
            format = Logging.Format.Multiline
        }
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()

private fun getDeviceName(): String {
    val manufacturer: String = Build.MANUFACTURER
    val model: String = Build.MODEL
    return if (model.lowercase(Locale.getDefault())
            .startsWith(manufacturer.lowercase(Locale.getDefault()))
    ) {
        capitalize(model)
    } else {
        capitalize(manufacturer) + " " + model
    }
}


private fun capitalize(s: String?): String {
    if (s.isNullOrEmpty()) {
        return ""
    }
    val first = s[0]
    return if (Character.isUpperCase(first)) s
     else first.uppercaseChar().toString() + s.substring(1)

}