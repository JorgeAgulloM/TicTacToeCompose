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

    private lateinit var userId: String
    private var _game = MutableStateFlow<GameModel?>(null)
    val game: StateFlow<GameModel?> = _game

    private var _winner = MutableStateFlow<PlayerType?>(null)
    val winner: StateFlow<PlayerType?> = _winner

    fun joinToGame(gameId: String, userId: String, owner: Boolean) {
        this.userId = userId
        if (owner) {
            joinToGameLikeOwner(gameId)
        } else {
            joinToGameLikeGuest(gameId)
        }
    }

    fun onItemSelected(position: Int) {
        val currentGame = _game.value ?: return

        if (currentGame.isGameReady &&
            currentGame.board[position] == PlayerType.Empty &&
            isMyTurn(currentGame.playerTurn)
        ) {
            viewModelScope.launch {
                val updateBoard = currentGame.board.toMutableList()
                updateBoard[position] = getPlayer() ?: PlayerType.Empty

                firebaseService.updateGame(
                    currentGame.copy(board = updateBoard, playerTurn = playerOpposite()!!)
                )
            }
        }
    }

    private fun joinToGameLikeOwner(gameId: String) {
        join(gameId)
    }

    private fun joinToGameLikeGuest(gameId: String) {
        viewModelScope.launch {

            firebaseService.joinToGame(gameId).take(1).collect { game ->
                val gameResult = game?.copy(
                    player2 = PlayerModel(
                        userId = userId,
                        playerType = PlayerType.SecondPlayer
                    )
                )
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
                val gameResult =
                    game?.copy(
                        isGameReady = game.player2 != null,
                        isMyTurn = isMyTurn(game.playerTurn)
                    )
                _game.update { gameResult }
                verifyWinner()
            }
        }
    }

    private fun isMyTurn(playerTurn: PlayerModel): Boolean = playerTurn.userId == userId

    private fun getPlayer(): PlayerType? {
        return when {
            (game.value?.player1?.userId == userId) -> PlayerType.FirstPlayer
            (game.value?.player2?.userId == userId) -> PlayerType.SecondPlayer
            else -> null
        }
    }

    private fun playerOpposite(): PlayerModel? {
        return when {
            (game.value?.player1?.userId == userId) -> _game.value?.player2
            (game.value?.player2?.userId == userId) -> _game.value?.player1
            else -> null
        }
    }

    private fun verifyWinner() {
        val board = _game.value?.board
        if (board != null && board.size == 9) {
            _winner.update {
                when {
                    isGameWon(board, PlayerType.FirstPlayer) -> PlayerType.FirstPlayer
                    isGameWon(board, PlayerType.SecondPlayer) -> PlayerType.SecondPlayer
                    else -> null
                }
            }
        }
    }

    private fun isGameWon(board: List<PlayerType>, playerType: PlayerType): Boolean {
        return when {
            // Row
            (board[0] == playerType && board[1] == playerType && board[2] == playerType) -> true
            (board[3] == playerType && board[4] == playerType && board[5] == playerType) -> true
            (board[6] == playerType && board[7] == playerType && board[8] == playerType) -> true

            // Columns
            (board[0] == playerType && board[3] == playerType && board[6] == playerType) -> true
            (board[1] == playerType && board[4] == playerType && board[7] == playerType) -> true
            (board[2] == playerType && board[5] == playerType && board[8] == playerType) -> true

            // Diagonals
            (board[0] == playerType && board[4] == playerType && board[8] == playerType) -> true
            (board[2] == playerType && board[4] == playerType && board[6] == playerType) -> true

            else -> false
        }

    }
}
