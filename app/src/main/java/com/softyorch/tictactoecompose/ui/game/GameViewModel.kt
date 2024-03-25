package com.softyorch.tictactoecompose.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softyorch.tictactoecompose.data.network.FirebaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val firebaseService: FirebaseService
) : ViewModel() {


    lateinit var userId: String

    fun joinToGame(gameId: String, userId: String, owner: Boolean) {
        this.userId = userId
        if (owner) {
            joinToGameLikeOwner(gameId)
        } else {
            joinToGameLikeGuest(gameId)
        }
    }

    private fun joinToGameLikeOwner(gameId: String) {
        viewModelScope.launch {
            firebaseService.joinToGame(gameId).collect {
                Log.i("BeerChatLog", it.toString())
            }
        }
    }

    private fun joinToGameLikeGuest(gameId: String) {
        TODO("Not yet implemented")
    }
}