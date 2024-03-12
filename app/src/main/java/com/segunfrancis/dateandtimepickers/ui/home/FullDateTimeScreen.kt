package com.segunfrancis.dateandtimepickers.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.segunfrancis.dateandtimepickers.util.OptionItem
import com.segunfrancis.dateandtimepickers.util.toDate
import com.segunfrancis.dateandtimepickers.util.toTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullDateTimeScreen(navController: NavHostController) {
    val type = navController.currentBackStackEntry?.arguments?.getString("type")
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(is24Hour = false)
    val dateRangePickerState = rememberDateRangePickerState()
    type?.let {
        when (OptionItem.valueOf(it)) {
            OptionItem.TIME_PICKER -> {
                val calender = Calendar.getInstance()
                calender.set(Calendar.MINUTE, timePickerState.minute)
                calender.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                FullTimeScreenContent(
                    time = calender.time.time.toTime(),
                    timePickerState = timePickerState
                )
            }

            OptionItem.DATE_PICKER -> {
                FullDateScreenContent(
                    date = datePickerState.selectedDateMillis.toDate(),
                    datePickerState = datePickerState
                )
            }

            OptionItem.DATE_RANGE_PICKER -> {
                FullDateRangeScreenContent(
                    dateRangePickerState = dateRangePickerState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullDateScreenContent(date: String, datePickerState: DatePickerState) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Spacer(Modifier.height(16.dp))
        Text(text = "Selected Date: ".plus(date), modifier = Modifier.padding(start = 16.dp))
        DatePicker(state = datePickerState, title = null, headline = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullTimeScreenContent(time: String, timePickerState: TimePickerState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Text(text = "Selected Time: ".plus(time), modifier = Modifier.padding(start = 16.dp))
        TimePicker(state = timePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullDateRangeScreenContent(dateRangePickerState: DateRangePickerState) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Selected Date: ".plus("${dateRangePickerState.selectedStartDateMillis.toDate()} - ${dateRangePickerState.selectedEndDateMillis.toDate()}"),
            modifier = Modifier.padding(start = 16.dp)
        )
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
