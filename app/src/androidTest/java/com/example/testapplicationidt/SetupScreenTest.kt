package com.example.testapplicationidt

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testapplicationidt.ui.setup.SetupScreen
import com.example.testapplicationidt.ui.setup.SetupViewModel
import com.example.testapplicationidt.ui.theme.TestApplicationIDTTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SetupScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun buildDisabledWhenColumnsExceedLimit() {
        composeTestRule.setContent {
            TestApplicationIDTTheme {
                SetupScreen(
                    viewModel = SetupViewModel(),
                    onBuild = { _, _ -> },
                )
            }
        }
        composeTestRule.onNodeWithTag("rows_input").performTextInput("5")
        composeTestRule.onNodeWithTag("columns_input").performTextInput("7")
        composeTestRule.onNodeWithTag("build_button").assertIsNotEnabled()
    }
}
