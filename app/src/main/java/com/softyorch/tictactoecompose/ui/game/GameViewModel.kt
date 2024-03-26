package com.softyorch.tictactoecompose.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softyorch.tictactoecompose.data.network.FirebaseService
import com.softyorch.tictactoecompose.ui.model.GameModel
import com.softyorch.tictactoecompose.ui.model.PlayerModel
import com.softyorch.tictactoecompose.ui.model.PlayerType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val firebaseService: FirebaseService
) : ViewModel() {

    lateinit var userId: String
    private var _game = MutableStateFlow<GameModel?>(null)
    val game: StateFlow<GameModel?> = _game

    fun joinToGame(gameId: String, userId: String, owner: Boolean) {
        this.userId = userId
        if (owner) {
            joinToGameLikeOwner(gameId)
        } else {
            joinToGameLikeGuest(gameId)
        }
    }

    private fun joinToGameLikeOwner(gameId: String) {
        join(gameId)
    }

    private fun joinToGameLikeGuest(gameId: String) {
        viewModelScope.launch {

            firebaseService.joinToGame(gameId).take(1).collect { game ->
                val gameResult = game?.copy(player2 = PlayerModel(
                    userId = userId,
                    playerType = PlayerType.SecondPlayer
                ))
                gameResult?.let {
                    firebaseService.updateGame(it)
                }
            }

            join(gameId)
        }
    }

    private fun join(gameId: String) {
        viewModelScope.launch {
            firebaseService.joinToGame(gameId).collect { game ->
                val gameResult = game?.copy(isGameReady = game.player2 != null, isMyTurn = isMyTurn())
                _game.update { gameResult }
            }
        }
    }

    private fun isMyTurn(): Boolean = game.value?.playerTurn?.userId == userId
}
