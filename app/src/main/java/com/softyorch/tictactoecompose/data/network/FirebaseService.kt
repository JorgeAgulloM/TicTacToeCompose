package com.softyorch.tictactoecompose.data.network

import com.google.firebase.database.DatabaseReference
import com.softyorch.tictactoecompose.data.network.model.GameData
import javax.inject.Inject

class FirebaseService @Inject constructor(private val reference: DatabaseReference) {

    companion object {
        private const val PATH = "games"
    }

    fun createGame(gameData: GameData) {
        val gameRef = reference.child(PATH).push()
        gameRef.setValue(gameData)
    }
}
