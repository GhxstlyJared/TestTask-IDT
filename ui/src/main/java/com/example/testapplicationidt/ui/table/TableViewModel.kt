package com.example.testapplicationidt.ui.table

import androidx.lifecycle.ViewModel
import com.example.testapplicationidt.domain.model.TableConfig
import com.example.testapplicationidt.domain.usecase.CreateTableUseCase
import com.example.testapplicationidt.domain.usecase.ToggleCellUseCase
import com.example.testapplicationidt.domain.usecase.UpdateCellValueUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TableViewModel(
    private val rows: Int,
    private val columns: Int,
    private val createTableUseCase: CreateTableUseCase,
    private val toggleCellUseCase: ToggleCellUseCase,
    private val updateCellValueUseCase: UpdateCellValueUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TableUiState(rows = rows, columns = columns))
    val uiState: StateFlow<TableUiState> = _uiState.asStateFlow()

    init {
        val cells = createTableUseCase(TableConfig(rows = rows, columns = columns))
        _uiState.update { it.copy(cells = cells) }
    }

    fun onCellClick(row: Int, column: Int) {
        _uiState.update { state ->
            state.copy(cells = toggleCellUseCase(state.cells, row, column))
        }
    }

    fun onCellDoubleClick(row: Int, column: Int) {
        val cell = _uiState.value.cells.first { it.row == row && it.column == column }
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
            state.copy(
                cells = updateCellValueUseCase(state.cells, editing.row, editing.column, editing.value),
                editingCell = null,
            )
        }
    }

    fun onEditDismiss() {
        _uiState.update { it.copy(editingCell = null) }
    }
}
