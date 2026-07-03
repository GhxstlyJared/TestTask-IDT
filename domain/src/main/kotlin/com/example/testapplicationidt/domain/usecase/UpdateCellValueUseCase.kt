package com.example.testapplicationidt.domain.usecase

import com.example.testapplicationidt.domain.model.TableCell

class UpdateCellValueUseCase {
    operator fun invoke(cells: List<TableCell>, row: Int, column: Int, value: String): List<TableCell> {
        return cells.map { cell ->
            if (cell.row == row && cell.column == column) {
                cell.copy(value = value)
            } else {
                cell
            }
        }
    }
}
