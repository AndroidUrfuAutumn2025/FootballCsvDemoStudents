package model

import model.enums.PlayerPosition
import model.enums.AgencyName
import model.enums.NationalityName

data class Player (
    val name: String,
    val team: Team,
    val position: PlayerPosition,
    val nationality: NationalityName,
    val agency: AgencyName?,
    val transferCost: Long,
    val participations: Int,
    val goals: Int,
    val assists: Int,
    val yellowCards: Int,
    val redCards: Int,
)