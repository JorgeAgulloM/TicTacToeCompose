package com.softyorch.tictactoecompose.data.network

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.snapshots
import com.softyorch.tictactoecompose.data.network.model.GameData
import com.softyorch.tictactoecompose.data.network.model.GameData.Companion.toData
import com.softyorch.tictactoecompose.data.network.model.GameData.Companion.toModel
import com.softyorch.tictactoecompose.ui.model.GameModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    fun joinToGame(gameId: String): Flow<GameModel?> =
        reference.database.reference.child("$PATH/$gameId")
            .snapshots.map { dataSnapshot ->
                dataSnapshot.getValue(GameData::class.java)?.toModel()
            }

    fun updateGame(game: GameModel) {
        setGame(game.toData())
    }

    private fun setGame(game: GameData) {
        reference.child(PATH).child(game.gameId!!).setValue(game)
    }
}
