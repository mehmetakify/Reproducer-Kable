package com.produce.project.mobile.viewmodel.utils

import com.produce.project.mobile.viewmodel.utils.Constants.WRONG_PIN_CODE_WAITING_MINUTE
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round

object Util {

    private const val chars = "0123456789abcdefghijklmnoprstuvyzABCDEFGHIJKLMNOPRSTUVYZ./*?+%&"

    fun getTimestamp(instant: RealmInstant): Long = getTimestampAsString(instant).toLong()
    fun getTimestampAsString(instant: RealmInstant): String =
        instant.epochSeconds.toString() + instant.nanosecondsOfSecond.toString().substring(0, 3)

    fun getLockType(lockId: String): String {
        return when(lockId.reversed()[3]) {
            '1' -> "safeLock"
            '2' -> "popupLock"
            '3' -> "cabinetLock"
            '4' -> "alarm"
            else -> ""
        }
    }

    fun randomGenerator(length: Int): String {
        var result = ""
        for (i in 0 until length) {
            result += chars.elementAt(floor(((0..9).random() / 10f) * chars.length).toInt())
        }
        return result
    }

    fun countOccurrences(s: String, ch: Char): Int {
        return s.length - s.replace(ch.toString(), "").length
    }

    suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
        map { async { f(it) } }.awaitAll()
    }

    fun getWorkingHour(text: String): String {
        return try {
            var numberVal = text.toFloat()
            if (numberVal < 0) numberVal = abs(numberVal)
            if (numberVal >= 24) return "24"
            if ((numberVal * 2) % 1.0 != 0.0) return numberVal.toInt().toString() + ".5"
            val isInt = numberVal % 1.0 == 0.0
            if (isInt && text.last() == '.') return text
            if (isInt) numberVal.toInt().toString() else numberVal.toString()
        } catch (e: Exception) {
            ""
        }
    }

    fun getTryAgainTime (disabledTime: Long): Pair<Int, Int> {
        val diff = floor(((disabledTime + WRONG_PIN_CODE_WAITING_MINUTE*60*1000 - getTimestamp(RealmInstant.now())) / 1000).toDouble())
        if (diff <= 0L) return Pair(0, 0)
        val minutes = floor((diff / 60)).toInt()
        val seconds = (diff % 60).toInt()

        return Pair(minutes, seconds)
    }

}