package com.example.testapplicationidt.ui.table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplicationidt.domain.model.TableConfig
import com.example.testapplicationidt.domain.usecase.CreateTableUseCase
import com.example.testapplicationidt.domain.usecase.UpdateCellValueUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TableViewModel(
    private val rows: Int,
    private val columns: Int,
    private val createTableUseCase: CreateTableUseCase,
    private val updateCellValueUseCase: UpdateCellValueUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TableUiState(rows = rows, columns = columns))
    val uiState: StateFlow<TableUiState> = _uiState.asStateFlow()

    init {
        loadTable()
    }

    fun retryLoad() {
        loadTable()
    }

    fun onCellClick(row: Int, column: Int) {
        val key = cellKey(row, column, columns)
        _uiState.update { state ->
            val highlightedKeys = if (key in state.highlightedKeys) {
                state.highlightedKeys - key
            } else {
                state.highlightedKeys + key
            }
            state.copy(highlightedKeys = highlightedKeys)
        }
    }

    fun onCellDoubleClick(row: Int, column: Int) {
        val key = cellKey(row, column, columns)
        val cell = _uiState.value.cells[key] ?: return
        _uiState.update {
            it.copy(editingCell = EditingCell(row = row, column = column, value = cell.value))
        }
    }

    fun onEditValueChange(value: String) {
        _uiState.update { state ->
            val editing = state.editingCell ?: return@update state
            state.copy(editingCell = editing.copy(value = value))
        }
    }

    fun onEditConfirm() {
        _uiState.update { state ->
            val editing = state.editingCell ?: return@update state
            val key = cellKey(editing.row, editing.column, columns)
            state.copy(
                cells = updateCellValueUseCase(state.cells, key, editing.value),
                editingCell = null,
            )
        }
    }

    fun onEditDismiss() {
        _uiState.update { it.copy(editingCell = null) }
    }

    private fun loadTable() {
        _uiState.update { it.copy(isLoading = true, loadError = false) }
        viewModelScope.launch {
            try {
                val cells = withContext(Dispatchers.Default) {
                    createTableUseCase(TableConfig(rows = rows, columns = columns))
                        .associateBy { cellKey(it.row, it.column, columns) }
                }
                _uiState.update { it.copy(cells = cells, isLoading = false, loadError = false) }
            } catch (_: Exception) {
                _uiState.update { it.copy(isLoading = false, loadError = true) }
            }
        }
    }
}
