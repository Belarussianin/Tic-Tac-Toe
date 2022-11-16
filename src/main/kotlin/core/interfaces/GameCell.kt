package core.interfaces

import core.Player

sealed interface GameCell {
    object EmptyCell : GameCell {
        override fun toString(): String = javaClass.simpleName
    }
    data class OccupiedCell(val player: Player) : GameCell
}