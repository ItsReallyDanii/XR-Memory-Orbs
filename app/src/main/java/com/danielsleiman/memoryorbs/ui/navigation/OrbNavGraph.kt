package com.danielsleiman.memoryorbs.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.danielsleiman.memoryorbs.ui.screens.OrbDetailScreen
import com.danielsleiman.memoryorbs.ui.screens.OrbEditScreen
import com.danielsleiman.memoryorbs.ui.screens.OrbListScreen
import com.danielsleiman.memoryorbs.ui.screens.RecallScreen
import com.danielsleiman.memoryorbs.ui.viewmodel.MemoryOrbViewModel

@Composable
fun OrbNavGraph(
    navController: NavHostController,
    viewModel: MemoryOrbViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.OrbList.route
    ) {
        composable(Screen.OrbList.route) {
            OrbListScreen(
                viewModel = viewModel,
                onOrbClick = { orbId ->
                    navController.navigate(Screen.OrbDetail.createRoute(orbId))
                },
                onRecallClick = {
                    navController.navigate(Screen.Recall.route)
                },
                onAddOrbClick = {
                    navController.navigate(Screen.OrbEdit.createRoute())
                }
            )
        }
        composable(Screen.Recall.route) {
            RecallScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onOrbClick = { orbId ->
                    navController.navigate(Screen.OrbDetail.createRoute(orbId))
                }
            )
        }
        composable(
            route = Screen.OrbDetail.route,
            arguments = listOf(navArgument("orbId") { type = NavType.IntType })
        ) { backStackEntry ->
            val orbId = backStackEntry.arguments?.getInt("orbId") ?: return@composable
            OrbDetailScreen(
                orbId = orbId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onEdit = { id ->
                    navController.navigate(Screen.OrbEdit.createRoute(id))
                },
                onDelete = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.OrbEdit.route,
            arguments = listOf(navArgument("orbId") { 
                type = NavType.IntType
                defaultValue = -1 // Using -1 to indicate new orb as IntType doesn't support null defaultValue easily
            })
        ) { backStackEntry ->
            val orbIdArg = backStackEntry.arguments?.getInt("orbId")
            val orbId = if (orbIdArg == -1) null else orbIdArg
            OrbEditScreen(
                orbId = orbId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }
    }
}
