package com.softyorch.tictactoecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.softyorch.tictactoecompose.ui.theme.TicTacToeComposeTheme
import dagger.hilt.android.AndroidEntryPoint

// Game List
    // Board : List<String> = 9 positions
    // GameId: String
    // Player1
        // PlayerId
        // PlayerType
    // Player2

    // PlayerTurn

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeComposeTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}
