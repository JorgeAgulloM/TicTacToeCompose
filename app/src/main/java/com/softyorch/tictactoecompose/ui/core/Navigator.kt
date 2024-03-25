package com.softyorch.tictactoecompose.ui.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.softyorch.tictactoecompose.ui.core.Routes.Game
import com.softyorch.tictactoecompose.ui.core.Routes.Home
import com.softyorch.tictactoecompose.ui.game.GameScreen
import com.softyorch.tictactoecompose.ui.home.HomeScreen

@Composable
fun ContentWrapper(navController: NavHostController) {

    NavHost(navController, startDestination = Home.route) {
        composable(Home.route) {
            HomeScreen { navController.navigate(Game.route) }
        }
        composable(Game.route) {
            GameScreen { navController.navigate(Home.route)}
        }
    }
}
