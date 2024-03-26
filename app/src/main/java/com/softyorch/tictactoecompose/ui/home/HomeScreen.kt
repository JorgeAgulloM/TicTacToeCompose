package com.softyorch.tictactoecompose.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softyorch.tictactoecompose.R
import com.softyorch.tictactoecompose.ui.theme.Accent
import com.softyorch.tictactoecompose.ui.theme.Background
import com.softyorch.tictactoecompose.ui.theme.Orange1
import com.softyorch.tictactoecompose.ui.theme.Orange2

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToGame: (String, String, Boolean) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(color = Background)
    ) {
        Header()
        Body(onCreateGame = { viewModel.onCreateGame(navigateToGame) }) { gameId ->
            viewModel.onJoinGame(gameId, navigateToGame)
        }
    }
}

@Composable
fun Body(
    onCreateGame: () -> Unit,
    onJoinGame: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(24.dp),
        backgroundColor = Background,
        border = BorderStroke(4.dp, Orange1),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            var createdGame by remember { mutableStateOf(value = true) }
            Switch(
                checked = createdGame,
                onCheckedChange = { createdGame = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Orange2,
                    uncheckedThumbColor = Accent
                )
            )
            Divider(modifier = Modifier.height(2.dp).padding(24.dp))
            AnimatedContent(targetState = createdGame, label = "") {
                when (it) {
                    true -> CreateGame(onCreateGame)
                    false -> JoinToGame(onJoinGame)
                }
            }
        }
    }
}

@Composable
fun Header() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.titactoe),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxSize().padding(top = 48.dp)
            )
        }
        Text(
            text = "TicTacFirebase",
            modifier = Modifier.padding(bottom = 32.dp),
            fontSize = 28.sp,
            color = Orange1,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CreateGame(onCreateGame: () -> Unit) {
    Button(
        onClick = { onCreateGame() }, modifier = Modifier.padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Orange1,
            contentColor = Accent
        )
    ) {
        Text(text = "Create game")
    }
}

@Composable
fun JoinToGame(onJoinGame: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Accent,
                focusedBorderColor = Orange1,
                unfocusedBorderColor = Accent,
                cursorColor = Orange2
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onJoinGame(text) },
            modifier = Modifier.padding(vertical = 8.dp),
            enabled = text.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Orange1,
                contentColor = Accent
            )
        ) {
            Text(text = "Join to game")
        }
    }


}