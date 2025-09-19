package model

class Player(
    var name: String = "",
    var team: Team = Team(),
    var position: String = "",
    var nationality: String = "",
    var agency: String = "",
    var transferCost: Long = 0,
    var participations: Int = 0,
    var goals: Int = 0,
    var assists: Int = 0,
    var yellowCards: Int = 0,
    var redCards: Int = 0
)
