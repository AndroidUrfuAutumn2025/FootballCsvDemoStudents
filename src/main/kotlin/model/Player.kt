package model

class Player(
    var name: String, var teamName: String, var teamCity: String,
    var position: String, var nationality: String, var agency: String,
    var transferCost: Int, var participations: Int, var goals: Int,
    var assists: Int, var yellowCards: Int, var redCards: Int
)
