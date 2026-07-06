package com.example.testapplicationidt.ui.setup

data class SetupUiState(
    val rows: String = "",
    val columns: String = "",
    val rowsError: SetupFieldError? = null,
    val columnsError: SetupFieldError? = null,
)
