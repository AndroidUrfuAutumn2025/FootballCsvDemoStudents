package model

enum class Position(val russianName: String) {
    GOALKEEPER("Вратарь"),
    DEFENDER("Защитник"),
    MIDFIELD("Полузащитник"),
    FORWARD("Нападающий");
    
    companion object {
        fun fromString(position: String): Position? {
            return values().find { it.name == position }
        }
    }
}
