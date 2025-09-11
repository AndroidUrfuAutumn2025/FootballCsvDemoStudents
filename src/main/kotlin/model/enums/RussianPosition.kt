package model.enums

enum class RussianPosition(val displayName: String) {
    GOALKEEPER("Вратарь"),
    DEFENDER("Защитник"),
    MIDFIELD("Полузащитник"),
    FORWARD("Нападающий");

    companion object {
        fun fromRussianPosition(engPosition: PlayerPosition): RussianPosition {
            return when (engPosition) {
                PlayerPosition.GOALKEEPER -> GOALKEEPER
                PlayerPosition.DEFENDER -> DEFENDER
                PlayerPosition.MIDFIELD -> MIDFIELD
                PlayerPosition.FORWARD -> FORWARD
            }
        }
    }
}