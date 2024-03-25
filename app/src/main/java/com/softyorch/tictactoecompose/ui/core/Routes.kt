package com.softyorch.tictactoecompose.ui.core

sealed class Routes(val route: String) {
    data object Home: Routes("home")
    data object Game: Routes("game")
}
