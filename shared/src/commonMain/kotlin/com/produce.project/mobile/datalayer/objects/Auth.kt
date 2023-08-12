package com.produce.project.mobile.datalayer.objects

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey

class Auth : RealmObject {
    var l: String? = null   // Language
    var e: String? = null   // Email
    var n: String? = null   // name
    var s: String? = null   // surname
    var w: Int = 0  // Wrong Pin Code Count
    var t: Long? = null // App Disabled Start Time
    var p: String? = null // PIN Code
    var b: Long? = null // Background Time
    var y: Boolean = false // Has Saved Lock
    var z: Boolean = false // For ios get background
}

class RealmLock : RealmObject {
    @Index
    var nickName: String = ""
    var password: String = ""
    var lockType: String = ""
    @Index
    var lockId: String = ""
}

class RealmOperation: RealmObject {
    var a: String = ""
    var b: Long = 0L
}

class RealmLockLog : RealmObject {
    @PrimaryKey
    var id: String = ""
    var action: String = ""
    var lockId: String = ""
    var platform: String = "Android"
    var operationList: RealmList<RealmOperation> = realmListOf()
    var battery: Int = 0
    var macNo: String = ""
    var userEmail: String? = null
}