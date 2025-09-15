package model

enum class Position(val russianName: String) {
    GOALKEEPER("Вратарь"),
    DEFENDER("Защитник"),
    MIDFIELD("Полузащитник"),
    FORWARD("Нападающий");

    companion object {
        fun fromString(value: String): Position? {
            return Position.entries.find {it.name.equals(value, ignoreCase=true)}
        }
    }
}
