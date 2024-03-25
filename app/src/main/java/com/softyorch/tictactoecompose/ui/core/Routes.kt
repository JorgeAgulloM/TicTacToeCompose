package com.softyorch.tictactoecompose.ui.core

sealed class Routes(val route: String) {
    object Home: Routes("home")
    object Game: Routes("game")
}
