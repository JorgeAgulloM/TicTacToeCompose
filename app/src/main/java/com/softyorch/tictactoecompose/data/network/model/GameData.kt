package com.softyorch.tictactoecompose.data.network.model

data class GameData(
    val board: List<Int?>? = null,
    val gameId: String? = null,
    val playerData1: PlayerData? = null,
    val playerData2: PlayerData? = null,
    val playerDataTurn: PlayerData? = null,
)
