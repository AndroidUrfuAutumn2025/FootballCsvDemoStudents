package model

enum class Position(val translate: String) {
    GOALKEEPER("Вратарь"),
    DEFENDER("Защитник"),
    MIDFIELD("Полузащитник"),
    FORWARD("Нападающий");
}

data class Person(
    val name: String,
    val team: String,
    val city: String,
    val position: Position,
    val nationality: String,
    val agency: String,
    val transferCost: Int?,
    val participations: Int?,
    val goals: Int?,
    val assists: Int?,
    val yellowCards: Int?,
    val redCards: Int?
    )


