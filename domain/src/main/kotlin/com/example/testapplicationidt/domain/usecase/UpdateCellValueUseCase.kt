package com.example.testapplicationidt.domain.usecase

import com.example.testapplicationidt.domain.model.TableCell

class UpdateCellValueUseCase {
    operator fun invoke(cells: Map<Int, TableCell>, key: Int, value: String): Map<Int, TableCell> {
        val cell = cells[key] ?: return cells
        return cells + (key to cell.copy(value = value))
    }
}
