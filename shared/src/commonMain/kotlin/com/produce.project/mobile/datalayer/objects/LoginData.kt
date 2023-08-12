package com.produce.project.mobile.datalayer.objects

import io.realm.kotlin.ext.toRealmList

data class Operation(
    val a: String,
    val b: Long
)

data class LockLog(
    val id: String,
    val action: String,
    val lockId: String,
    val platform: String,
    val operationList: List<Operation>,
    val battery: Int,
    val macNo: String,
    val userEmail: String? = null
)

data class Lock constructor(
    val nickName: String,
    val password: String,
    val lockId: String,
    val lockType: String
)

fun Operation.toRealmOperation(): RealmOperation {
    return RealmOperation().apply {
        a = this@toRealmOperation.a
        b = this@toRealmOperation.b
    }
}

fun RealmOperation.toOperation(): Operation {
    return Operation(this@toOperation.a, this@toOperation.b)
}

fun LockLog.toRealmLockLog(): RealmLockLog {
    return RealmLockLog().apply {
        id = this@toRealmLockLog.id
        action = this@toRealmLockLog.action
        lockId = this@toRealmLockLog.lockId
        platform = this@toRealmLockLog.platform
        operationList = this@toRealmLockLog.operationList.map { it.toRealmOperation() }.toRealmList()
        battery = this@toRealmLockLog.battery
        macNo = this@toRealmLockLog.macNo
        userEmail = this@toRealmLockLog.userEmail
    }
}

fun RealmLockLog.toLockLog(): LockLog {
    return LockLog(
        id = this@toLockLog.id,
        action = this@toLockLog.action,
        lockId = this@toLockLog.lockId,
        platform = this@toLockLog.platform,
        operationList = this@toLockLog.operationList.map { it.toOperation() },
        battery = this@toLockLog.battery,
        macNo = this@toLockLog.macNo,
        userEmail = this@toLockLog.userEmail
    )
}

fun Lock.toRealmLock(): RealmLock {
    return RealmLock().apply {
        nickName = this@toRealmLock.nickName
        password = enchip(this@toRealmLock.password)
        lockId = this@toRealmLock.lockId
        lockType = this@toRealmLock.lockType
    }
}

fun RealmLock.toLock(): Lock {
    return Lock(
        nickName = this@toLock.nickName,
        password = dechip(this@toLock.password),
        lockId = this@toLock.lockId,
        lockType = this@toLock.lockType
    )
}

private fun enchip(str: String): String {
    return str.toList().mapIndexed { index, char ->
        Char(char.code + 5*(index+2))
    }.joinToString("")
}
private fun dechip(str: String): String {
    return str.toList().mapIndexed { index, char ->
        Char(char.code - 5*(index+2))
    }.joinToString("")
}