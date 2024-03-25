package com.softyorch.tictactoecompose.ui.home

import androidx.lifecycle.ViewModel
import com.softyorch.tictactoecompose.data.network.FirebaseService
import com.softyorch.tictactoecompose.data.network.model.GameData
import com.softyorch.tictactoecompose.data.network.model.PlayerData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseService: FirebaseService
) : ViewModel() {

    fun onCreateGame(navigateToGame: (String, String, Boolean) -> Unit) {
        val game = createNewGame()
        val gameId = firebaseService.createGame(game)
        val userId =  game.playerData1?.userId.orEmpty()
        val owner = true
        navigateToGame(gameId, userId, owner)
    }

    fun onJoinGame(gameId: String, navigateToGame: (String, String, Boolean) -> Unit) {
        val owner = false
        navigateToGame(gameId, userId(), owner)
    }

    private fun userId(): String = PlayerData.generateId

    private fun createNewGame(): GameData {
        val currentPlayer = PlayerData(playerType = 1)
        return GameData(
            board = List(9) { 0 },
            playerData1 = currentPlayer,
            playerDataTurn = currentPlayer,
            playerData2 = null
        )
    }
}
