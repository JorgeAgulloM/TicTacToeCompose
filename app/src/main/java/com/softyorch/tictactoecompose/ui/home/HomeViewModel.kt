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

    fun onCreateGame() {
        firebaseService.createGame(createNewGame())
    }

    private fun createNewGame(): GameData {
        val currentPlayer = PlayerData(playerType = 1)
        return GameData(
            board = List(9) { 0 },
            playerData1 = currentPlayer,
            playerDataTurn = currentPlayer,
            playerData2 = null
        )
    }

    fun onJoinGame(id: String) {

    }
}