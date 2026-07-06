package com.example.testapplicationidt.ui.setup

sealed interface SetupFieldError {
    data object Required : SetupFieldError
    data object InvalidNumber : SetupFieldError
    data class OutOfRange(val min: Int, val max: Int) : SetupFieldError
}
