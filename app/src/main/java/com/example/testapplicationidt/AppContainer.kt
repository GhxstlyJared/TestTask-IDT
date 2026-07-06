package com.example.testapplicationidt

import com.example.testapplicationidt.data.RandomStringGenerator
import com.example.testapplicationidt.data.TableRepositoryImpl
import com.example.testapplicationidt.domain.repository.TableRepository
import com.example.testapplicationidt.domain.usecase.CreateTableUseCase
import com.example.testapplicationidt.domain.usecase.UpdateCellValueUseCase

class AppContainer {
    private val repository: TableRepository = TableRepositoryImpl(RandomStringGenerator())
    val createTableUseCase: CreateTableUseCase = CreateTableUseCase(repository)
    val updateCellValueUseCase: UpdateCellValueUseCase = UpdateCellValueUseCase()
}
