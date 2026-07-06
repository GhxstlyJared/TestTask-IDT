package com.example.testapplicationidt.ui.table

import com.example.testapplicationidt.domain.model.TableCell

data class TableUiState(
    val rows: Int = 0,
    val columns: Int = 0,
    val cells: Map<Int, TableCell> = emptyMap(),
    val highlightedKeys: Set<Int> = emptySet(),
    val editingCell: EditingCell? = null,
    val isLoading: Boolean = true,
    val loadError: Boolean = false,
)
