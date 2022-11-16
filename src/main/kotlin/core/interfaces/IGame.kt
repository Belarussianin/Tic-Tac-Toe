package core.interfaces

import core.Player
import kotlinx.coroutines.flow.StateFlow

interface IGame {
    val score: IGameScore
    val initPlayer: Player
    val currentPlayer: StateFlow<Player>
    val initStatus: GameStatus
    val status: StateFlow<GameStatus>
    val field: IGameField

    fun reset()
    fun makeMove(rowIndex: Int, columnIndex: Int): Pair<Boolean, String>
}