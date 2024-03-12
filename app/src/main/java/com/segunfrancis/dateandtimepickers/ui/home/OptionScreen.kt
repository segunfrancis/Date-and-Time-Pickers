package com.segunfrancis.dateandtimepickers.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.segunfrancis.dateandtimepickers.util.OptionItem
import com.segunfrancis.dateandtimepickers.util.Route

@Composable
fun OptionScreen(navController: NavHostController) {
    OptionScreenContent {
        when (it) {
            OptionScreenUiActions.OnDateTimeDialogClick -> {
                navController.navigate(Route.DATE_TIME_DIALOG_SCREEN)
            }

            OptionScreenUiActions.OnFullscreenDatePickerClick -> {
                navController.navigate(Route.FULL_DATE_TIME_SCREEN.plus("/${OptionItem.DATE_PICKER.name}"))
            }

            OptionScreenUiActions.OnFullscreenDateRangePickerClick -> {
                navController.navigate(Route.FULL_DATE_TIME_SCREEN.plus("/${OptionItem.DATE_RANGE_PICKER.name}"))
            }

            OptionScreenUiActions.OnFullscreenTimePickerClick -> {
                navController.navigate(Route.FULL_DATE_TIME_SCREEN.plus("/${OptionItem.TIME_PICKER.name}"))
            }
        }
    }
}

@Composable
fun OptionScreenContent(onAction: (OptionScreenUiActions) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(onClick = { onAction(OptionScreenUiActions.OnFullscreenDatePickerClick) }) {
            Text(text = "Fullscreen Date Picker")
        }
        ElevatedButton(onClick = { onAction(OptionScreenUiActions.OnFullscreenDateRangePickerClick) }) {
            Text(text = "Fullscreen Date-Range Picker")
        }
        ElevatedButton(onClick = { onAction(OptionScreenUiActions.OnFullscreenTimePickerClick) }) {
            Text(text = "Fullscreen Time Picker")
        }
        ElevatedButton(onClick = { onAction(OptionScreenUiActions.OnDateTimeDialogClick) }) {
            Text(text = "Date Time Dialog")
        }
    }
}

sealed interface OptionScreenUiActions {
    data object OnFullscreenDatePickerClick : OptionScreenUiActions
    data object OnFullscreenTimePickerClick : OptionScreenUiActions
    data object OnFullscreenDateRangePickerClick : OptionScreenUiActions
    data object OnDateTimeDialogClick : OptionScreenUiActions
}

@Preview
@Composable
fun OptionScreenPreview() {
    OptionScreenContent(onAction = {})
}
