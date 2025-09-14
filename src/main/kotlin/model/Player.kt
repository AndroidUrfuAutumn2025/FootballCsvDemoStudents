package model

import model.Team
import model.Position

class Player(
    val name: String,
    val team: Team,
    val city: String,
    val position: Position,
    val nationality: String,
    var agency: String,
    var transferCost: Double,
    val participations: Int,
    val goals: Int,
    val assists: Int,
    val yellowCards: Int,
    val redCards: Int
)
