package com.example.testapplicationidt.domain.usecase

import com.example.testapplicationidt.domain.model.TableCell

class ToggleCellUseCase {
    operator fun invoke(cells: List<TableCell>, row: Int, column: Int): List<TableCell> {
        return cells.map { cell ->
            if (cell.row == row && cell.column == column) {
                cell.copy(isHighlighted = !cell.isHighlighted)
            } else {
                cell
            }
        }
    }
}
