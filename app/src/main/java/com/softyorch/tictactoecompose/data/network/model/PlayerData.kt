package com.softyorch.tictactoecompose.data.network.model

import java.util.Calendar

data class PlayerData(
    val userId: String? = generateId,
    val playerType: Int? = null
) {
    companion object {
        val generateId: String = Calendar.getInstance().timeInMillis.hashCode().toString()
    }
}
