package model

// Простой класс команды
data class Team(
    val name: String,
    val players: List<Player> = emptyList() // Список игроков в команде
)