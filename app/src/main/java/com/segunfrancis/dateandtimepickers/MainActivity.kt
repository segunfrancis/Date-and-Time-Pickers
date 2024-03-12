package com.segunfrancis.dateandtimepickers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segunfrancis.dateandtimepickers.ui.home.FullDateTimeScreen
import com.segunfrancis.dateandtimepickers.ui.home.DateTimeDialogScreen
import com.segunfrancis.dateandtimepickers.ui.home.OptionScreen
import com.segunfrancis.dateandtimepickers.ui.theme.DateAndTimePickersTheme
import com.segunfrancis.dateandtimepickers.util.Route

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            DateAndTimePickersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = Route.OPTIONS_SCREEN) {
                        composable(Route.OPTIONS_SCREEN) {
                            OptionScreen(navController)
                        }
                        composable(Route.DATE_TIME_DIALOG_SCREEN) {
                            DateTimeDialogScreen()
                        }
                        composable(Route.FULL_DATE_TIME_SCREEN.plus("/{type}")) {
                            FullDateTimeScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DateAndTimePickersTheme {
        Greeting("Android")
    }
}