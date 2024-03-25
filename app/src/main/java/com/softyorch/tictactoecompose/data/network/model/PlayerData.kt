package com.softyorch.tictactoecompose.data.network.model

import com.softyorch.tictactoecompose.ui.model.PlayerModel
import com.softyorch.tictactoecompose.ui.model.PlayerType
import java.util.Calendar

data class PlayerData(
    val userId: String? = generateId,
    val playerType: Int? = null
) {
    companion object {
        val generateId: String = Calendar.getInstance().timeInMillis.hashCode().toString()

        fun PlayerData.toModel(): PlayerModel = PlayerModel(
            userId!!, PlayerType.gamePlayerById(playerType)
        )

        fun PlayerModel.toData(): PlayerData = PlayerData(userId, playerType.id)
    }
}
