@file:OptIn(ExperimentalMaterial3Api::class)

package com.segunfrancis.dateandtimepickers.ui.home

import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.segunfrancis.dateandtimepickers.ui.TimePickerDialog
import com.segunfrancis.dateandtimepickers.util.toDate
import com.segunfrancis.dateandtimepickers.util.toTime
import com.segunfrancis.dateandtimepickers.util.toYear
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.util.Calendar
import java.util.TimeZone

@Composable
fun DateTimeDialogScreen() {

    // region Date Picker
    val datePickerState =
        rememberDatePickerState(
            initialDisplayedMonthMillis = System.currentTimeMillis(),
            yearRange = 2000..System.currentTimeMillis().toYear().toInt(),
            initialDisplayMode = DisplayMode.Input,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val dayOfWeek = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.of("UTC"))
                            .toLocalDate().dayOfWeek
                        dayOfWeek != DayOfWeek.SUNDAY && dayOfWeek != DayOfWeek.SATURDAY
                    } else {
                        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                        calendar.timeInMillis = utcTimeMillis
                        calendar[Calendar.DAY_OF_WEEK] != Calendar.SUNDAY &&
                                calendar[Calendar.DAY_OF_WEEK] != Calendar.SATURDAY
                    }
                }

                override fun isSelectableYear(year: Int): Boolean {
                    return year >= 2023
                }
            }
        )
    val showDatePicker = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf("") }
    // endregion

    // region Date Range Picker
    val dateRangePickerState = rememberDateRangePickerState(yearRange = 2024..2024)
    val showDateRangePicker = remember { mutableStateOf(false) }
    val selectedDateRange = remember { mutableStateOf("") }
    // endregion

    // region Time Range Picker
    val timePickerState = rememberTimePickerState(is24Hour = false)
    val showTimePicker = remember { mutableStateOf(false) }
    val selectedTime = remember { mutableStateOf("") }
    // endregion

    DateTimeDialogScreenContent(
        selectedDate = selectedDate.value,
        selectedDateRange = selectedDateRange.value,
        selectedTime = selectedTime.value,
        onClick = {
            when (it) {
                DateTimeDialogScreenUiActions.OnDatePickerClick -> {
                    showDatePicker.value = showDatePicker.value.not()
                }

                DateTimeDialogScreenUiActions.OnDateRangePickerClick -> {
                    showDateRangePicker.value = showDateRangePicker.value.not()
                }

                DateTimeDialogScreenUiActions.OnTimePickerClick -> {
                    showTimePicker.value = showTimePicker.value.not()
                }
            }
        })

    if (showDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker.value = false },
            confirmButton = {
                TextButton(
                    onClick = { showDatePicker.value = false },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker.value = false }) {
                    Text(text = "Dismiss")
                }
            }) {
            selectedDate.value = datePickerState.selectedDateMillis.toDate()
            DatePicker(state = datePickerState)
        }
    }

    if (showDateRangePicker.value) {
        DatePickerDialog(
            onDismissRequest = { showDateRangePicker.value = false },
            confirmButton = {
                TextButton(onClick = { showDateRangePicker.value = false }) {
                    Text(text = "Confirm")
                }
            }) {
            selectedDateRange.value =
                "${dateRangePickerState.selectedStartDateMillis.toDate()} - ${dateRangePickerState.selectedEndDateMillis.toDate()}"
            DateRangePicker(state = dateRangePickerState, title = null, headline = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = dateRangePickerState.selectedStartDateMillis.toDate()
                            .ifEmpty { "Start date" },
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = " - ",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = dateRangePickerState.selectedEndDateMillis.toDate()
                            .ifEmpty { "End date" },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            })
        }
    }

    if (showTimePicker.value) {
        TimePickerDialog(
            onCancel = { showTimePicker.value = false },
            onConfirm = { showTimePicker.value = false },
            content = { displayMode ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                calendar.set(Calendar.MINUTE, timePickerState.minute)
                selectedTime.value = calendar.time.time.toTime()
                AnimatedContent(
                    targetState = displayMode == DisplayMode.Input,
                    label = "Time picker animation",
                ) {
                    if (it) {
                        TimePicker(state = timePickerState)
                    } else {
                        TimeInput(state = timePickerState)
                    }
                }
            })
    }
}

@Composable
fun DateTimeDialogScreenContent(
    onClick: (DateTimeDialogScreenUiActions) -> Unit,
    selectedDate: String,
    selectedDateRange: String,
    selectedTime: String
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(16.dp))
            ElevatedButton(onClick = { onClick(DateTimeDialogScreenUiActions.OnDatePickerClick) }) {
                Text(text = "Date Picker")
            }
            Spacer(Modifier.width(16.dp))
            Text(text = selectedDate, style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(16.dp))
            ElevatedButton(onClick = { onClick(DateTimeDialogScreenUiActions.OnDateRangePickerClick) }) {
                Text(text = "Date Range Picker")
            }
            Spacer(Modifier.width(16.dp))
            Text(text = selectedDateRange, style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(16.dp))
            ElevatedButton(onClick = { onClick(DateTimeDialogScreenUiActions.OnTimePickerClick) }) {
                Text(text = "Time Picker")
            }
            Spacer(Modifier.width(16.dp))
            Text(text = selectedTime, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

sealed interface DateTimeDialogScreenUiActions {
    data object OnDatePickerClick : DateTimeDialogScreenUiActions
    data object OnTimePickerClick : DateTimeDialogScreenUiActions
    data object OnDateRangePickerClick : DateTimeDialogScreenUiActions
}

@Preview(showSystemUi = true)
@Composable
fun DateTimeDialogScreenPreview() {
    DateTimeDialogScreenContent(
        selectedDate = "12 April",
        selectedTime = "12:05 am",
        selectedDateRange = "11-12-12 - 12-12-12",
        onClick = {})
}