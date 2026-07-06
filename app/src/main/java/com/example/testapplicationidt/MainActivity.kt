package com.example.testapplicationidt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.testapplicationidt.ui.TabletRequiredScreen
import com.example.testapplicationidt.ui.navigation.AppNavigation
import com.example.testapplicationidt.ui.theme.TestApplicationIDTTheme

class MainActivity : ComponentActivity() {
    private val container = AppContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestApplicationIDTTheme {
//                if (isTablet()) {
                    AppNavigation(
                        setupViewModelFactory = SetupViewModelFactory(),
                        tableViewModelFactory = { rows, columns ->
                            TableViewModelFactory(rows, columns, container)
                        },
                    )
//                } else {
//                    TabletRequiredScreen()
//                }
            }
        }
    }

    private fun isTablet(): Boolean {
        return resources.configuration.smallestScreenWidthDp >= 600
    }
}
