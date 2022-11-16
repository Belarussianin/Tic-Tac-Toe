import core.Game

fun main() {
    val game = Game()
    println(game.field.state.value)
    game.makeMove(0, 0)
    println(game.field.state.value)
    game.makeMove(0, 1)
    println(game.field.state.value)
    game.makeMove(1, 1)
    println(game.field.state.value)
    game.makeMove(0, 2)
    println(game.field.state.value)
    game.makeMove(2, 2)
    println(game.field.state.value)
    game.makeMove(1, 1)
    println(game.field.state.value)
    println(game.score.state.value)
    println(game.status.value)
}