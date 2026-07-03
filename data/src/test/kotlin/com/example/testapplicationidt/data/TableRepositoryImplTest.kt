package com.example.testapplicationidt.data

import com.example.testapplicationidt.domain.model.TableConfig
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TableRepositoryImplTest {
    @Test
    fun generateTable_returnsCorrectSizeAndCoordinates() {
        val repository = TableRepositoryImpl(RandomStringGenerator())
        val cells = repository.generateTable(TableConfig(rows = 3, columns = 2))
        assertEquals(6, cells.size)
        assertEquals(0, cells[0].row)
        assertEquals(0, cells[0].column)
        assertTrue(cells[0].value.length == 6)
        assertEquals(2, cells[5].row)
        assertEquals(1, cells[5].column)
    }
}
