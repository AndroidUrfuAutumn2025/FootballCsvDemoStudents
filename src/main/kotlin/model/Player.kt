package model
enum class Position(val russianName: String) {
    MIDFIELD("Полузащитник"),
    DEFENDER("Защитник"),
    GOALKEEPER("Вратарь"),
    FORWARD("Нападающий");

    companion object {
        fun getByValue(s: String): Position {
            return entries.find { it.name.equals(s, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown position: $s")
        }
    }
}

class Player(
    val name: String,
    val team: Team,
    val position: Position,
    val nationality: String,
    val agency: String,
    val transferCost: Int,
    val participationCount: Int,
    val goals: Int,
    val assists: Int,
    val yellowCards: Int,
    val redCards: Int
)
