import core.TicTacToe
import core.interfaces.ITicTacToe

fun main() {
    TicTacToe().apply {
        printFieldState()
        makeMove(0, 0)
        printFieldState()
        makeMove(0, 1)
        printFieldState()
        makeMove(1, 1)
        printFieldState()
        makeMove(0, 2)
        printFieldState()
        makeMove(2, 2)
        printFieldState()
        makeMove(1, 1)
        printFieldState()
        println(score.state.value)
        println(status.value)
    }
}

fun ITicTacToe.printFieldState() {
    println(field.state.value.joinToString("\n"))
}