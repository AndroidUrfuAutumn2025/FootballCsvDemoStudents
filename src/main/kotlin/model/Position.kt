package model

enum class Position(val namePosition: String) {
    GOALKEEPER("Вратарь"),
    DEFENDER("Защитник"),
    MIDFIELD("Полузащитник"),
    FORWARD("Нападающий"),
    UNKNOWN("Неизвестно");

    companion object {
        fun getString(value: String): Position {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: UNKNOWN
        }
    }
}