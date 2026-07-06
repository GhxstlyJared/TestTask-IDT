package com.example.testapplicationidt.ui.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testapplicationidt.ui.R
import com.example.testapplicationidt.ui.theme.TestApplicationIDTTheme

@Composable
fun SetupScreen(
    viewModel: SetupViewModel,
    onBuild: (Int, Int) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    SetupScreenContent(
        state = state,
        onRowsChange = viewModel::onRowsChange,
        onColumnsChange = viewModel::onColumnsChange,
        onBuild = {
            val config = viewModel.buildConfig()
            if (config != null) {
                onBuild(config.rows, config.columns)
            }
        },
    )
}

@Composable
fun SetupScreenContent(
    state: SetupUiState,
    onRowsChange: (String) -> Unit,
    onColumnsChange: (String) -> Unit,
    onBuild: () -> Unit,
) {
    val canBuild = state.rowsError == null &&
        state.columnsError == null &&
        state.rows.isNotBlank() &&
        state.columns.isNotBlank()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.widthIn(max = 400.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.setup_title),
                style = MaterialTheme.typography.headlineMedium,
            )
            OutlinedTextField(
                value = state.rows,
                onValueChange = onRowsChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("rows_input"),
                label = { Text(stringResource(R.string.setup_rows_label)) },
                isError = state.rowsError != null,
                supportingText = state.rowsError?.let { error ->
                    { Text(error.toMessage(isRows = true)) }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )
            OutlinedTextField(
                value = state.columns,
                onValueChange = onColumnsChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("columns_input"),
                label = { Text(stringResource(R.string.setup_columns_label)) },
                isError = state.columnsError != null,
                supportingText = state.columnsError?.let { error ->
                    { Text(error.toMessage(isRows = false)) }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )
            Button(
                onClick = onBuild,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("build_button"),
                enabled = canBuild,
            ) {
                Text(stringResource(R.string.setup_build))
            }
        }
    }
}

@Composable
private fun SetupFieldError.toMessage(isRows: Boolean): String {
    return when (this) {
        SetupFieldError.Required -> stringResource(R.string.error_required)
        SetupFieldError.InvalidNumber -> stringResource(R.string.error_invalid_number)
        is SetupFieldError.OutOfRange -> {
            if (isRows) {
                stringResource(R.string.error_rows_range, min, max)
            } else {
                stringResource(R.string.error_columns_range, min, max)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 600)
@Composable
private fun SetupScreenPreview() {
    TestApplicationIDTTheme {
        SetupScreenContent(
            state = SetupUiState(rows = "10", columns = "4"),
            onRowsChange = {},
            onColumnsChange = {},
            onBuild = {},
        )
    }
}
