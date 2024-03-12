package com.segunfrancis.dateandtimepickers.util

import java.text.SimpleDateFormat
import java.util.Locale

fun Long?.toDate(): String {
    val dateFormat = SimpleDateFormat("d MMM, y", Locale.getDefault())
    return try {
        dateFormat.format(this)
    } catch (t: Throwable) {
        t.printStackTrace()
        ""
    }
}

fun Long?.toYear(): String {
    val dateFormat = SimpleDateFormat("y", Locale.getDefault())
    return try {
        dateFormat.format(this)
    } catch (t: Throwable) {
        t.printStackTrace()
        ""
    }
}

fun Long?.toTime(): String {
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    return try {
        timeFormat.format(this)
    } catch (t: Throwable) {
        t.printStackTrace()
        ""
    }
}

object Route {
    const val DATE_TIME_DIALOG_SCREEN = "home_screen"
    const val FULL_DATE_TIME_SCREEN = "full_date_time_screen"
    const val OPTIONS_SCREEN = "options_screen"
}

enum class OptionItem {
    TIME_PICKER, DATE_PICKER, DATE_RANGE_PICKER
}
