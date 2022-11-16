package core.interfaces

import core.Player

sealed interface GameStatus {
    object Started : GameStatus
    object Draw : GameStatus
    data class Win(val player: Player) : GameStatus
}