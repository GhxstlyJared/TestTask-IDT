package com.example.testapplicationidt.ui.setup

import androidx.lifecycle.ViewModel
import com.example.testapplicationidt.domain.model.TableConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SetupViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SetupUiState())
    val uiState: StateFlow<SetupUiState> = _uiState.asStateFlow()

    fun onRowsChange(value: String) {
        _uiState.update { it.copy(rows = value, rowsError = validateRows(value)) }
    }

    fun onColumnsChange(value: String) {
        _uiState.update { it.copy(columns = value, columnsError = validateColumns(value)) }
    }

    fun buildConfig(): TableConfig? {
        val state = _uiState.value
        val rowsError = validateRows(state.rows)
        val columnsError = validateColumns(state.columns)
        _uiState.update { it.copy(rowsError = rowsError, columnsError = columnsError) }
        if (rowsError != null || columnsError != null) {
            return null
        }
        return TableConfig(
            rows = state.rows.toInt(),
            columns = state.columns.toInt(),
        )
    }

    private fun validateRows(value: String): String? {
        if (value.isBlank()) {
            return "Required"
        }
        val number = value.toIntOrNull()
        if (number == null) {
            return "Invalid number"
        }
        if (number !in SetupConstraints.MIN_ROWS..SetupConstraints.MAX_ROWS) {
            return "Must be ${SetupConstraints.MIN_ROWS}-${SetupConstraints.MAX_ROWS}"
        }
        return null
    }

    private fun validateColumns(value: String): String? {
        if (value.isBlank()) {
            return "Required"
        }
        val number = value.toIntOrNull()
        if (number == null) {
            return "Invalid number"
        }
        if (number !in SetupConstraints.MIN_COLUMNS..SetupConstraints.MAX_COLUMNS) {
            return "Must be ${SetupConstraints.MIN_COLUMNS}-${SetupConstraints.MAX_COLUMNS}"
        }
        return null
    }
}
