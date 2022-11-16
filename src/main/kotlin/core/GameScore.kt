package core

import core.interfaces.IGameScore
import core.interfaces.IGameScore.Companion.getStandardScoreState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameScore(
    override val initState: Map<Player, Int> = getStandardScoreState()
) : IGameScore {

    private val _state = MutableStateFlow(initState)
    override val state = _state.asStateFlow()

    override fun reset() {
        _state.update { initState }
    }

    override fun set(player: Player, score: Int) {
        _state.update { it.plus(player to score) }
    }
}