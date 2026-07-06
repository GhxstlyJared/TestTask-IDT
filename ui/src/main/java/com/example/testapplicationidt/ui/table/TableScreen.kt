package com.example.testapplicationidt.ui.table

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableScreen(
    viewModel: TableViewModel,
    onBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${state.rows} x ${state.columns}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
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
                                onClick = { viewModel.onCellClick(row, column) },
                                onDoubleClick = { viewModel.onCellDoubleClick(row, column) },
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
            }
        }
    }
    state.editingCell?.let { editing ->
        AlertDialog(
            onDismissRequest = viewModel::onEditDismiss,
            title = { Text("Edit cell") },
            text = {
                OutlinedTextField(
                    value = editing.value,
                    onValueChange = viewModel::onEditValueChange,
                    singleLine = true,
                )
            },
            confirmButton = {
                TextButton(onClick = viewModel::onEditConfirm) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::onEditDismiss) {
                    Text("Cancel")
                }
            },
        )
    }
}
