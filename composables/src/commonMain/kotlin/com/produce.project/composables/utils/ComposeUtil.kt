package com.produce.project.composables.utils

import com.produce.project.mobile.viewmodel.utils.Constants
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import java.util.*
import kotlin.math.abs
import kotlin.math.floor

object ComposeUtil {

    private fun addZero(number: Int): String {
        return "${if (number < 10) "0" else ""}$number"
    }

    fun getDate(time: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time + calendar.timeZone.rawOffset
        return "${addZero(calendar.get(Calendar.DATE))}." +
                "${addZero(calendar.get(Calendar.MONTH) + 1)}." +
                "${calendar.get(Calendar.YEAR)}"
    }

    fun getDueDate(
        time: Long,
        year: String,
        month: String,
        day: String,
        ago: String,
        later: String,
        today: String,
        yesterday: String,
        tomorrow: String
    ): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        val todayDate = Calendar.getInstance()
        val difference = calendar.timeInMillis - todayDate.timeInMillis
        val absoluteDifference = abs(difference)
        val dayDifference = (floor((calendar.timeInMillis / 86400000).toDouble()) - floor((todayDate.timeInMillis / 86400000).toDouble())).toInt()
        val absoluteDayDifference = abs(dayDifference)
        val isLater = difference > 0
        var number: Int
        val calendarHour = calendar.get(Calendar.HOUR_OF_DAY)
        val hourVal = if (calendarHour < 10) "0$calendarHour" else calendarHour.toString()
        val calendarMinute = calendar.get(Calendar.MINUTE)
        val minuteVal = if (calendarMinute < 10) "0$calendarMinute" else calendarMinute.toString()
        val calendarTime = "$hourVal:${minuteVal}"
        if (calendar.get(Calendar.YEAR) == todayDate.get(Calendar.YEAR)) {
            if (calendar.get(Calendar.MONTH) == todayDate.get(Calendar.MONTH)) {
                if (calendar.get(Calendar.DAY_OF_MONTH) == todayDate.get(Calendar.DAY_OF_MONTH)) {
                    return "$today $calendarTime"
                }
                return when (absoluteDayDifference) {
                    1 -> if (isLater) "$tomorrow $calendarTime" else "$yesterday $calendarTime"
                    else -> if (absoluteDayDifference < 30) {
                        return if (isLater) {
                            "$absoluteDayDifference $day $later"
                        } else {
                            "$absoluteDayDifference $day $ago"
                        }
                    } else {
                        number = floor((absoluteDifference / 2592000000).toDouble()).toInt()
                        if (number < 12) {
                            return if (isLater) {
                                "$number $month $later"
                            } else {
                                "$number $month $ago"
                            }
                        } else {
                            number = floor((absoluteDifference / 31536000000).toDouble()).toInt()
                            return if (isLater) {
                                "$number $year $later"
                            } else {
                                "$number $year $ago"
                            }
                        }
                    }
                }
            } else {
                return when (absoluteDayDifference) {
                    1 -> if (isLater) "$tomorrow $calendarTime" else "$yesterday $calendarTime"
                    else -> if (absoluteDayDifference < 30) {
                        return if (isLater) {
                            "$absoluteDayDifference $day $later"
                        } else {
                            "$absoluteDayDifference $day $ago"
                        }
                    } else {
                        number = floor((absoluteDifference / 2592000000).toDouble()).toInt()
                        if (number < 12) {
                            return if (isLater) {
                                "$number $month $later"
                            } else {
                                "$number $month $ago"
                            }
                        } else {
                            number = floor((absoluteDifference / 31536000000).toDouble()).toInt()
                            return if (isLater) {
                                "$number $year $later"
                            } else {
                                "$number $year $ago"
                            }
                        }
                    }
                }
            }
        } else {
            return when (absoluteDayDifference) {
                1 -> if (isLater) "$tomorrow $calendarTime" else "$yesterday $calendarTime"
                else -> if (absoluteDayDifference < 30) {
                    return if (isLater) {
                        "$absoluteDayDifference $day $later"
                    } else {
                        "$absoluteDayDifference $day $ago"
                    }
                } else {
                    number = floor((absoluteDifference / 2592000000).toDouble()).toInt()
                    if (number < 12) {
                        return if (isLater) {
                            "$number $month $later"
                        } else {
                            "$number $month $ago"
                        }
                    } else {
                        number = floor((absoluteDifference / 31536000000).toDouble()).toInt()
                        return if (isLater) {
                            "$number $year $later"
                        } else {
                            "$number $year $ago"
                        }
                    }
                }
            }
        }
    }

    fun handleApiError(error: Exception): Constants.ProgressState {
        if (error.cause is IOException) {
            when {
                error.localizedMessage?.contains("timeout") == true -> {
                    return Constants.ProgressState.API_ERROR_TIMEOUT
                }
                error.localizedMessage?.contains("connect") == true -> {
                    return Constants.ProgressState.API_ERROR_CONNECT
                }

            }
        }
        return Constants.ProgressState.API_ERROR
    }

    suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
        map { async { f(it) } }.awaitAll()
    }
}