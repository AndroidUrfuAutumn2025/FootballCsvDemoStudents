package model

data class Player(
    val name: String,
    val team: Team,
    val position: String,
    val nationality: String,
    val agency: String?,
    val transferCost: Long,
    val participations: Int,
    val goals: Int,
    val assists: Int,
    val yellowCards: Int,
    val redCards: Int
) {
    private fun toRussianPosition(): String  {
        return when (position.uppercase().trim()) {
            "GOALKEEPER" -> "Вратарь"
            "DEFENDER" -> "Защитник"
            "MIDFIELD" -> "Полузащитник"
            "FORWARD" -> "Нападающий"
            else -> "Неизвестный"
        }
    }

    val russianPosition: String get() = toRussianPosition()
}
