package com.produce.project.mobile.datalayer.realm

import com.produce.project.mobile.datalayer.objects.*
import com.produce.project.mobile.viewmodel.utils.Constants.WRONG_PIN_CODE_TRY
import com.produce.project.mobile.viewmodel.utils.Util.getTimestamp
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RealmDatabase {

    private val realm: Realm by lazy {
        val configuration: RealmConfiguration = RealmConfiguration.Builder(
            schema = setOf(
                Auth::class,
                RealmLock::class,
                RealmLockLog::class,
                RealmOperation::class
            )
        ).name("alPsdI2").schemaVersion(51).build()
        Realm.open(configuration)
    }

    fun getStartData(): Auth? {
        return realm.query<Auth>().first().find()
    }

    fun getStartDataFlow(): Flow<Auth?>? {
        return realm.query<Auth>().first().find()?.asFlow()?.map { it.obj }
    }

    fun getStartDataCommonFlow(): CFlow<Auth?>? {
        return getStartDataFlow()?.wrap()
    }

    fun updateStartData(
        defaultEmail: String,
        name: String,
        surname: String,
        pin: String? = null
    ) {
        realm.query<Auth>()
            .first()
            .find()
            ?.also {
                realm.writeBlocking {
                    findLatest(it)?.apply {
                        e = defaultEmail
                        n = name
                        s = surname
                        w = 0
                        t = null
                        p = pin ?: p
                    }
                }
            } ?: realm.writeBlocking {
            copyToRealm(
                Auth()
            ).apply {
                e = defaultEmail
                n = name
                s = surname
                w = 0
                t = null
                p = pin ?: p
            }
        }
    }

    fun updateLanguage(
        language: String
    ) {
        realm.query<Auth>()
            .first()
            .find()
            ?.also {
                realm.writeBlocking {
                    findLatest(it)?.apply {
                        l = language
                    }
                }
            } ?: realm.writeBlocking {
            copyToRealm(
                Auth()
            ).apply {
                l = language
            }
        }
    }

    fun updatePin(
        pin: String?
    ) {
        realm.query<Auth>()
            .first()
            .find()
            ?.also {
                realm.writeBlocking {
                    findLatest(it)?.apply {
                        p = pin
                    }
                }
            }
    }

    fun removePin() {
        realm.query<Auth>()
            .first()
            .find()
            ?.also {
                realm.writeBlocking {
                    findLatest(it)?.apply {
                        p = null
                    }
                }
            }
    }

    fun incrementWrongPin() {
        realm.query<Auth>()
            .first()
            .find()
            ?.also {
                realm.writeBlocking {
                    findLatest(it)?.apply {
                        w += 1
                        t = if (w >= WRONG_PIN_CODE_TRY) getTimestamp(RealmInstant.now()) else null
                    }
                }
            } ?: realm.writeBlocking {
            copyToRealm(
                Auth()
            ).apply {
                w = 1
            }
        }
    }

    fun timeFinish() {
        realm.query<Auth>()
            .first()
            .find()
            ?.also {
                realm.writeBlocking {
                    findLatest(it)?.apply {
                        w = 0
                        t = null
                    }
                }
            }
    }

    fun saveBackgroundTime(isBackground: Boolean = false, isRemove: Boolean) {
        realm.query<Auth>()
            .first()
            .find()
            ?.also {
                realm.writeBlocking {
                    findLatest(it)?.apply {
                        if (isRemove) {
                            b = null
                            z = false
                        } else {
                            b = getTimestamp(RealmInstant.now())
                            z = isBackground
                        }
                    }
                }
            }
    }

    fun saveLock(name: String, password: String, lockId: String, lockType: String) {
        println("REALM saveLock")
        realm.query<RealmLock>("lockId = $0", lockId)
            .first()
            .find()
            ?.also {
                realm.writeBlocking {
                    findLatest(it)?.apply {
                        nickName = name
                        this.password = enchip(password)
                    }
                }
            } ?: realm.writeBlocking {
            copyToRealm(
                RealmLock()
            ).apply {
                nickName = name
                this.password = enchip(password)
                this.lockId = lockId
                this.lockType = lockType
            }
        }
    }

    fun deleteLock(id: String) {
        realm.writeBlocking {
            val query = this.query<RealmLock>("lockId = $0", id)
            delete(query)
        }
    }

    fun getAllLocks(): List<RealmLock> {
        return realm.query<RealmLock>().find().toList()
    }

    fun removeLocks() {
        realm.writeBlocking {
            val query = this.query<RealmLock>()
            delete(query)
        }
    }

    fun getLock(id: String): RealmLock? {
        return realm.query<RealmLock>("lockId = $0", id).first().find()
    }

    fun getLockLogs(id: String): RealmLockLog? {
        return realm.query<RealmLockLog>("id = $0", id).first().find()
    }

    fun getAllLockLogs(): List<RealmLockLog> {
        return realm.query<RealmLockLog>().find().toList()
    }

    fun removeLockLog(id: String) {
        realm.writeBlocking {
            val query = this.query<RealmLockLog>("id = $0", id)
            delete(query)
        }
    }
    fun updateOrAddLockLog(lockLog: RealmLockLog) {
        realm.query<RealmLockLog>("id = $0", lockLog.id)
            .first()
            .find()
            ?.also { previousLockLog ->
                realm.writeBlocking {
                    findLatest(previousLockLog)?.apply {
                        battery = lockLog.battery
                        operationList =
                            (operationList.toList().plus(lockLog.operationList)).toRealmList()
                    }
                }
            } ?: realm.writeBlocking {
            copyToRealm(lockLog)
        }
    }
}

private fun enchip(str: String): String {
    return str.toList().mapIndexed { index, char ->
        Char(char.code + 5*(index+2))
    }.joinToString("")
}