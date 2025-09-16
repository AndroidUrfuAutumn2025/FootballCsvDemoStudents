package model

import model.Team

class Player (val name: String, val team: Team, val position: PlayerPosition,
              val nationality: String, val agency: String?, val transferCost: Float,
              val participations: Int, val goals: Int, val assists: Int, val yellowCards: Int,
              val redCards: Int) {
}

enum class PlayerPosition (val s: String){
    DEFENDER("DEFENDER"),
    MIDFIELD("MIDFIELD"),
    GOALKEEPER("GOALKEEPER"),
    FORWARD("FORWARD")
}
