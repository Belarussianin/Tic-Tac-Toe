package core

enum class Player {
    X, O;

    operator fun not() = when (this) {
        X -> O
        O -> X
    }
}