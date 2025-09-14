package model

enum class Position(val positionName : String) {
    MIDFIELD("MIDFIELD"),
    DEFENDER("DEFENDER"),
    FORWARD("FORWARD"),
    GOALKEEPER("GOALKEEPER");

    fun toRussian(): String {
        return when (this) {
            MIDFIELD -> "Полузащитник"
            DEFENDER -> "Защитник"
            FORWARD -> "Нападающий"
            GOALKEEPER -> "Вратарь"
        }
    }
}
