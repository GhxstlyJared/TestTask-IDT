package com.example.testapplicationidt.data

import com.example.testapplicationidt.domain.model.TableCell
import com.example.testapplicationidt.domain.model.TableConfig
import com.example.testapplicationidt.domain.repository.TableRepository

class TableRepositoryImpl(
    private val generator: RandomStringGenerator,
) : TableRepository {
    override fun generateTable(config: TableConfig): List<TableCell> {
        return (0 until config.rows).flatMap { row ->
            (0 until config.columns).map { column ->
                TableCell(
                    row = row,
                    column = column,
                    value = generator.next(),
                )
            }
        }
    }
}
