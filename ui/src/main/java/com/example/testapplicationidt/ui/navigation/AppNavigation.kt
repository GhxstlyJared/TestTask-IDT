package com.example.testapplicationidt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testapplicationidt.ui.setup.SetupScreen
import com.example.testapplicationidt.ui.setup.SetupViewModel
import com.example.testapplicationidt.ui.table.TableScreen
import com.example.testapplicationidt.ui.table.TableViewModel

@Composable
fun AppNavigation(
    setupViewModelFactory: ViewModelProvider.Factory,
    tableViewModelFactory: (rows: Int, columns: Int) -> ViewModelProvider.Factory,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SETUP,
    ) {
        composable(Routes.SETUP) {
            val viewModel: SetupViewModel = viewModel(factory = setupViewModelFactory)
            SetupScreen(
                viewModel = viewModel,
                onBuild = { rows, columns ->
                    navController.navigate(Routes.table(rows, columns))
                },
            )
        }
        composable(
            route = Routes.TABLE,
            arguments = listOf(
                navArgument("rows") { type = NavType.IntType },
                navArgument("columns") { type = NavType.IntType },
            ),
        ) { entry ->
            val rows = entry.arguments?.getInt("rows") ?: return@composable
            val columns = entry.arguments?.getInt("columns") ?: return@composable
            val viewModel: TableViewModel = viewModel(
                key = "table-$rows-$columns",
                factory = tableViewModelFactory(rows, columns),
            )
            TableScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
