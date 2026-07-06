package com.example.testapplicationidt.domain.usecase

import com.example.testapplicationidt.domain.model.TableCell
import com.example.testapplicationidt.domain.model.TableConfig
import com.example.testapplicationidt.domain.repository.TableRepository
import org.junit.Assert.assertEquals
import org.junit.Test

class CreateTableUseCaseTest {
    @Test
    fun invoke_delegatesToRepository() {
        val expected = listOf(TableCell(0, 0, "test"))
        val repository = object : TableRepository {
            override fun generateTable(config: TableConfig): List<TableCell> {
                assertEquals(2, config.rows)
                assertEquals(3, config.columns)
                return expected
            }
        }
        val useCase = CreateTableUseCase(repository)
        val result = useCase(TableConfig(rows = 2, columns = 3))
        assertEquals(expected, result)
    }
}

class UpdateCellValueUseCaseTest {
    @Test
    fun invoke_updatesValue() {
        val cells = mapOf(0 to TableCell(0, 0, "old"))
        val useCase = UpdateCellValueUseCase()
        val result = useCase(cells, key = 0, value = "new")
        assertEquals("new", result.getValue(0).value)
    }
}
