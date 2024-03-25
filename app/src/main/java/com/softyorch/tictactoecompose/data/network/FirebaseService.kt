package com.softyorch.tictactoecompose.data.network

import com.google.firebase.database.DatabaseReference
import com.softyorch.tictactoecompose.data.network.model.GameData
import javax.inject.Inject

class FirebaseService @Inject constructor(private val reference: DatabaseReference) {

    companion object {
        private const val PATH = "games"
    }

    fun createGame(gameData: GameData): String {
        val gameRef = reference.child(PATH).push()
        val key = gameRef.key
        val newGame = gameData.copy(gameId = key)
        gameRef.setValue(newGame)
        return newGame.gameId.orEmpty()
    }
}
