package core

import core.interfaces.GameCell
import core.interfaces.GameStatus
import core.interfaces.IGame
import core.interfaces.IGameField
import core.interfaces.IGameScore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update

class Game(
    override val score: IGameScore = GameScore(),
    override val initPlayer: Player = Player.X,
    override val initStatus: GameStatus = GameStatus.Started,
    override val field: IGameField = GameField(),
) : IGame {

    private val _currentPlayer = MutableStateFlow(initPlayer)
    override val currentPlayer = _currentPlayer.asStateFlow()

    private val _status = MutableStateFlow(initStatus)
    override val status = _status.asStateFlow()

    override fun reset() {
        score.reset()
        _currentPlayer.update { initPlayer }
        _status.update { initStatus }
        field.reset()
    }

    /**
     * returns pair: is make move successful and message if not
     */
    override fun makeMove(rowIndex: Int, columnIndex: Int): Pair<Boolean, String> {
        return when (_status.value) {
            GameStatus.Started -> {
                field.set(rowIndex, columnIndex, GameCell.OccupiedCell(_currentPlayer.getAndUpdate { !it }))
                statusUpdate()
                true to ""
            }

            GameStatus.Draw -> {
                false to "${_status.value}"
            }

            is GameStatus.Win -> {
                false to "${_status.value}"
            }
        }
    }

    private fun statusUpdate() {
        _status.getAndUpdate { checkForDraw() ?: checkForWin() ?: it }
        when (val newStatus = _status.value) {
            is GameStatus.Win -> score.set(newStatus.player, score.state.value.getOrDefault(newStatus.player, 0) + 1)
            else -> {}
        }
    }

    private fun checkForWin(): GameStatus? {
        field.state.value.apply {
            forEach { row ->
                when (row) {
                    List(row.size) { Player.X } -> return GameStatus.Win(Player.X)
                    List(row.size) { Player.O } -> return GameStatus.Win(Player.O)
                }
            }
            for (columnIndex in indices) {
                when (val column = List(size) { this[it][columnIndex] }) {
                    List(column.size) { Player.X } -> return GameStatus.Win(Player.X)
                    List(column.size) { Player.O } -> return GameStatus.Win(Player.O)
                }
            }
            val mainDiagonal = List(size) { this[it][it] }
            val secondDiagonal = List(size) { this[it][lastIndex - it] }
            when {
                mainDiagonal.all { it is GameCell.OccupiedCell && it.player == Player.X } -> {
                    return GameStatus.Win(Player.X)
                }

                secondDiagonal.all { it is GameCell.OccupiedCell && it.player == Player.O } -> {
                    return GameStatus.Win(Player.O)
                }
            }
        }
        return null
    }

    private fun checkForDraw(): GameStatus? {
        field.state.value.apply {
            return when {
                flatten().all { it is GameCell.OccupiedCell } -> checkForWin() ?: GameStatus.Draw
                else -> null
            }
        }
    }
}