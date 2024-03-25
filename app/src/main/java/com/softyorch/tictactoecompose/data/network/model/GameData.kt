package com.softyorch.tictactoecompose.data.network.model

import com.softyorch.tictactoecompose.data.network.model.PlayerData.Companion.toData
import com.softyorch.tictactoecompose.data.network.model.PlayerData.Companion.toModel
import com.softyorch.tictactoecompose.ui.model.GameModel
import com.softyorch.tictactoecompose.ui.model.PlayerType

data class GameData(
    val board: List<Int?>? = null,
    val gameId: String? = null,
    val playerData1: PlayerData? = null,
    val playerData2: PlayerData? = null,
    val playerDataTurn: PlayerData? = null,
) {
    companion object {
        fun GameData.toModel(): GameModel = GameModel(
            board = board?.map { PlayerType.gamePlayerById(it) } ?: mutableListOf(),
            gameId = gameId.orEmpty(),
            player1 = playerData1!!.toModel(),
            player2 = playerData2?.toModel(),
            playerTurn = playerDataTurn!!.toModel(),
        )

        fun GameModel.toData(): GameData = GameData(
            board = board.map { it.id },
            gameId = gameId,
            player1.toData(),
            player2?.toData(),
            playerTurn.toData()
        )
    }
}
