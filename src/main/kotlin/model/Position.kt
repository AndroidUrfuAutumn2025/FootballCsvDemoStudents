package model

enum class Position(val russian: String) {
    GOALKEEPER("Вратарь"),
    DEFENDER("Защитник"),
    MIDFIELD("Полузащитник"),
    FORWARD("Нападающий");

    companion object {
        fun fromEnglish(value: String): Position? {
            return Position.entries.find { it.name.equals(value, ignoreCase = true) }
        }
    }
}