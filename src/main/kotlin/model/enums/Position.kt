package model.enums

enum class Position(val translate: String) {
    DEFENDER("Защитник"),
    GOALKEEPER("Вратарь"),
    MIDFIELD("Полузащитник"),
    FORWARD("Нападающий"),
    UNKNOWN("Неизвестная позиция");

    companion object {
        fun fromString(value: String): Position {
            return try {
                valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                UNKNOWN
            }
        }
    }
}