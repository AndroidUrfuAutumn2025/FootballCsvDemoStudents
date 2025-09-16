package model

data class Player(
    val name: String,
    val team: Team,
    val position: String,
    val nationality: String,
    val agency: String?,
    val transferValue: Double,
    val matchesPlayed: Int,
    val goals: Int,
    val assists: Int,
    val yellowCards: Int,
    val redCards: Int
)