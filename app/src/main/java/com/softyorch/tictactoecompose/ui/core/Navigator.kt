package com.softyorch.tictactoecompose.ui.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.softyorch.tictactoecompose.ui.core.Routes.Game
import com.softyorch.tictactoecompose.ui.core.Routes.Home
import com.softyorch.tictactoecompose.ui.game.GameScreen
import com.softyorch.tictactoecompose.ui.home.HomeScreen

@Composable
fun ContentWrapper(navController: NavHostController) {

    NavHost(navController, startDestination = Home.route) {
        composable(Home.route) {
            HomeScreen { gameId, userId, owner ->
                navController.navigate(Game.createRoute(gameId, userId, owner))
            }
        }
        composable(
            Game.route,
            arguments = listOf(
                navArgument("gameId") { type = NavType.StringType },
                navArgument("userId") { type = NavType.StringType },
                navArgument("owner") { type = NavType.BoolType },
            )
        ) {
            GameScreen(
                gameId = it.arguments?.getString("gameId").orEmpty(),
                userId = it.arguments?.getString("userId").orEmpty(),
                owner = it.arguments?.getBoolean("owner") ?: false
            ) { navController.navigate(Home.route) }
        }
    }
}
