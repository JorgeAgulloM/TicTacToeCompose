package com.softyorch.tictactoecompose.ui.model

sealed class PlayerType(val id: Int, val symbol: String) {
    object FirstPlayer : PlayerType(2, "X")
    object SecondPlayer : PlayerType(3, "O")
    object Empty : PlayerType(0, "")

    companion object {
        fun gamePlayerById(id: Int?): PlayerType = when (id) {
            FirstPlayer.id -> FirstPlayer
            SecondPlayer.id -> SecondPlayer
            else -> Empty
        }
    }
}
