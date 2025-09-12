package model.enums

enum class Position(val displayName: String) {
    MIDFIELD("Полузащитник"),
    DEFENDER("Защитник"),
    FORWARD("Нападающий"),
    GOALKEEPER("Вратарь");

    companion object {
        fun find(value: String): Position? {
            return entries.find { it.name == value }
        }
    }
}