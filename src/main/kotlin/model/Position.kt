package model

enum class Position {
    GOALKEEPER, DEFENDER, MIDFIELD, FORWARD, UNKNOWN;

    companion object {
        fun fromString(position: String): Position? {
            return values().find { it.name == position }
        }

        fun getRussianPositionName(position: Position): String {
            return when (position) {
                GOALKEEPER -> "Вратарь"
                DEFENDER -> "Защитник"
                MIDFIELD -> "Полузащитник"
                FORWARD -> "Нападающий"
                UNKNOWN -> "Неизвестно"
            }
        }
    }
}