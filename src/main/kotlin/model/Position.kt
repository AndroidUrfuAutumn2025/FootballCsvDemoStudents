package model

enum class Position(var russianName: String) {
    GOALKEEPER("Вратарь"),
    DEFENDER("Защитник"),
    MIDFIELD("Полузащитник"),
    FORWARD("Нападающий"),
    UNKNOWN("Неизвестный");

    companion object {
        fun parseString(value: String): Position {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: UNKNOWN
        }
    }
}