package model

import model.enums.Position

data class Player(
    val name: String,
    val team: Team,
    val position: Position,
    val nationality: String,
    val agencyName: String,
    val transferCost: Long,
    val participations: Int,
    val goals: Int,
    val assists: Int,
    val yellowCards: Int,
    val redCards: Int
)
