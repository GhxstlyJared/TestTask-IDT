package com.example.testapplicationidt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapplicationidt.ui.setup.SetupViewModel
import com.example.testapplicationidt.ui.table.TableViewModel

class SetupViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SetupViewModel() as T
    }
}

class TableViewModelFactory(
    private val rows: Int,
    private val columns: Int,
    private val container: AppContainer,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TableViewModel(
            rows = rows,
            columns = columns,
            createTableUseCase = container.createTableUseCase,
            updateCellValueUseCase = container.updateCellValueUseCase,
        ) as T
    }
}
