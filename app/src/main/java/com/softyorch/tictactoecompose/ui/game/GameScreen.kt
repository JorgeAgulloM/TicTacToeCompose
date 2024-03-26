package com.softyorch.tictactoecompose.ui.game


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softyorch.tictactoecompose.ui.model.GameModel
import com.softyorch.tictactoecompose.ui.model.PlayerType


@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    gameId: String,
    userId: String,
    owner: Boolean,
    navigateToHome: () -> Unit
) {

    LaunchedEffect(true) {
        viewModel.joinToGame(gameId, userId, owner)
    }

    val game: GameModel? by viewModel.game.collectAsState()
    val winner: PlayerType? by viewModel.winner.collectAsState()

    if (winner != null)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val winnerPlayer = if (winner == PlayerType.FirstPlayer) "Player 1" else "Player 2"
            Text(text = "Ganador!!\n$winnerPlayer")
        }
    else
        Board(game, onItemSelected = { position -> viewModel.onItemSelected(position) })
}

@Composable
fun Board(game: GameModel?, onItemSelected: (Int) -> Unit) {
    if (game == null) return

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = game.gameId)

        val status = if (game.isGameReady) {
            if (game.isMyTurn) {
                "Tu turno"
            } else {
                "Turno del rival"
            }
        } else {
            "Esperando a Jugador 2"
        }

        Text(text = status)

        Row {
            GameItem(game.board[0]) { onItemSelected(0) }
            GameItem(game.board[1]) { onItemSelected(1) }
            GameItem(game.board[2]) { onItemSelected(2) }
        }
        Row {
            GameItem(game.board[3]) { onItemSelected(3) }
            GameItem(game.board[4]) { onItemSelected(4) }
            GameItem(game.board[5]) { onItemSelected(5) }
        }
        Row {
            GameItem(game.board[6]) { onItemSelected(6) }
            GameItem(game.board[7]) { onItemSelected(7) }
            GameItem(game.board[8]) { onItemSelected(8) }
        }
    }
}

@Composable
fun GameItem(playerType: PlayerType, onItemSelected: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .size(64.dp)
            .border(BorderStroke(2.dp, color = Color.Black)).clickable { onItemSelected() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = playerType.symbol)
    }
}