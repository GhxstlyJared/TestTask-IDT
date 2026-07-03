package com.example.testapplicationidt.domain.repository

import com.example.testapplicationidt.domain.model.TableCell
import com.example.testapplicationidt.domain.model.TableConfig

interface TableRepository {
    fun generateTable(config: TableConfig): List<TableCell>
}
