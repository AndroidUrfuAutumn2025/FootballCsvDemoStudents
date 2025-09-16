package model

class Player(val name: String,
             val team: Team,
             val position: Position,
             val nationality: String,
             val agency: String?,
             val transferCost: Float,
             val participations: Int, // Количество участий в матчах
             val goals: Int,
             val assists: Int,
             val yellowCards: Int,
             val redCards: Int){

    override fun toString(): String {
        return this.name + " - " + this.redCards
    }
}


enum class Position(name: String){
    DEFENDER("DEFENDER"),
    MIDFIELD("MIDFIELD"),
    GOALKEEPER("GOALKEEPER"),
    FORWARD("FORWARD")
}