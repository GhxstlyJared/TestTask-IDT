package com.example.testapplicationidt.ui.navigation

object Routes {
    const val SETUP = "setup"
    const val TABLE = "table/{rows}/{columns}"

    fun table(rows: Int, columns: Int): String = "table/$rows/$columns"
}
