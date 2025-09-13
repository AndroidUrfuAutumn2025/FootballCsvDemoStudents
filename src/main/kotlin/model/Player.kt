package model

enum class Position(val title: String) {
    MIDFIELD("Полузащитник"),
    DEFENDER("Защитник"),
    FORWARD("Нападающий"),
    GOALKEEPER("Вратарь");

    companion object {
        fun getByName(name: String) = entries.find { it.name == name }
    }
}
data class Player(
    val name: String,
    val teamName: String,
    val city: String,
    val position: Position?,
    val nationality: String,
    val agency: String?,
    val transferCost: Int,
    val participations: Int,
    val goals: Int,
    val assists: Int,
    val yellowCards: Int,
    val redCards: Int
)
