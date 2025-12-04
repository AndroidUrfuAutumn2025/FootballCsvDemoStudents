package model

enum class Position(val ruText: String) {
    FORWARD(ruText = "Нападающий"),
    DEFENDER(ruText = "Защитник"),
    MIDFIELD(ruText = "Полузащитник"),
    GOALKEEPER(ruText = "Вратарь"),
    UNKNOWN(ruText = "Неизвестно"),
}