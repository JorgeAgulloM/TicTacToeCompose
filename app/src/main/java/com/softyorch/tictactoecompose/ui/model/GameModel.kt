package com.softyorch.tictactoecompose.ui.model

data class GameModel(
    val board: List<PlayerType>,
    val gameId: String,
    val player1: PlayerModel,
    val player2: PlayerModel?,
    val playerTurn: PlayerModel,
    val isGameReady: Boolean = false,
    val isMyTurn: Boolean = false,
)
