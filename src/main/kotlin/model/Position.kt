package model

enum class Position() {
    Goalkeeper, Defender, Midfield, Forward, Unknown;

    val russianName: String
        get() = when (this) {
            Goalkeeper -> "Вратарь"
            Defender -> "Защитник"
            Midfield -> "Полузащитник"
            Forward -> "Нападающий"
            Unknown -> "Неизвестный"
        }

    companion object {
        fun parseString(value: String): Position {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: Unknown
        }
    }
}