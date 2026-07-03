package com.example.testapplicationidt.ui.setup

data class SetupUiState(
    val rows: String = "",
    val columns: String = "",
    val rowsError: String? = null,
    val columnsError: String? = null,
)
