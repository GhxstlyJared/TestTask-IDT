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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SetupScreen(
    viewModel: SetupViewModel,
    onBuild: (Int, Int) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
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
                text = "Table size",
                style = MaterialTheme.typography.headlineMedium,
            )
            OutlinedTextField(
                value = state.rows,
                onValueChange = viewModel::onRowsChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Rows") },
                isError = state.rowsError != null,
                supportingText = state.rowsError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )
            OutlinedTextField(
                value = state.columns,
                onValueChange = viewModel::onColumnsChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Columns") },
                isError = state.columnsError != null,
                supportingText = state.columnsError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )
            Button(
                onClick = {
                    val config = viewModel.buildConfig()
                    if (config != null) {
                        onBuild(config.rows, config.columns)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = canBuild,
            ) {
                Text("Build")
            }
        }
    }
}
