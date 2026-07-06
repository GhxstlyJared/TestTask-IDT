package com.example.testapplicationidt.ui.table

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testapplicationidt.domain.model.TableCell
import com.example.testapplicationidt.ui.R
import com.example.testapplicationidt.ui.theme.TestApplicationIDTTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableScreen(
    viewModel: TableViewModel,
    onBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    BackHandler(onBack = onBack)
    TableScreenContent(
        state = state,
        onBack = onBack,
        onRetry = viewModel::retryLoad,
        onCellClick = viewModel::onCellClick,
        onCellDoubleClick = viewModel::onCellDoubleClick,
        onEditValueChange = viewModel::onEditValueChange,
        onEditConfirm = viewModel::onEditConfirm,
        onEditDismiss = viewModel::onEditDismiss,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableScreenContent(
    state: TableUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onCellClick: (Int, Int) -> Unit,
    onCellDoubleClick: (Int, Int) -> Unit,
    onEditValueChange: (String) -> Unit,
    onEditConfirm: () -> Unit,
    onEditDismiss: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.table_title, state.rows, state.columns))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
            )
        },
    ) { padding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            state.loadError -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = stringResource(R.string.load_error))
                    Button(
                        onClick = onRetry,
                        modifier = Modifier.padding(top = 16.dp),
                    ) {
                        Text(stringResource(R.string.retry))
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                ) {
                    items(state.rows, key = { it }) { row ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                        ) {
                            for (column in 0 until state.columns) {
                                val key = cellKey(row, column, state.columns)
                                val cell = state.cells.getValue(key)
                                TableCellItem(
                                    cell = cell,
                                    isHighlighted = key in state.highlightedKeys,
                                    onClick = { onCellClick(row, column) },
                                    onDoubleClick = { onCellDoubleClick(row, column) },
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    state.editingCell?.let { editing ->
        AlertDialog(
            onDismissRequest = onEditDismiss,
            title = { Text(stringResource(R.string.edit_cell_title)) },
            text = {
                OutlinedTextField(
                    value = editing.value,
                    onValueChange = onEditValueChange,
                    singleLine = true,
                )
            },
            confirmButton = {
                TextButton(onClick = onEditConfirm) {
                    Text(stringResource(R.string.save))
                }
            },
            dismissButton = {
                TextButton(onClick = onEditDismiss) {
                    Text(stringResource(R.string.cancel))
                }
            },
        )
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 600)
@Composable
private fun TableScreenPreview() {
    val cells = mapOf(
        0 to TableCell(0, 0, "abc123"),
        1 to TableCell(0, 1, "def456"),
        2 to TableCell(1, 0, "ghi789"),
        3 to TableCell(1, 1, "jkl012"),
    )
    TestApplicationIDTTheme {
        TableScreenContent(
            state = TableUiState(
                rows = 2,
                columns = 2,
                cells = cells,
                highlightedKeys = setOf(0),
                isLoading = false,
            ),
            onBack = {},
            onRetry = {},
            onCellClick = { _, _ -> },
            onCellDoubleClick = { _, _ -> },
            onEditValueChange = {},
            onEditConfirm = {},
            onEditDismiss = {},
        )
    }
}
