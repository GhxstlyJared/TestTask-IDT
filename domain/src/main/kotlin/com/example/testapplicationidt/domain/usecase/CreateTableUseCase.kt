package com.example.testapplicationidt.domain.usecase

import com.example.testapplicationidt.domain.model.TableCell
import com.example.testapplicationidt.domain.model.TableConfig
import com.example.testapplicationidt.domain.repository.TableRepository

class CreateTableUseCase(
    private val repository: TableRepository,
) {
    operator fun invoke(config: TableConfig): List<TableCell> {
        return repository.generateTable(config)
    }
}
