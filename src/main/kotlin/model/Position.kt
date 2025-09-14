package model

/**
 * Позиция игрока на футбольном поле
 */
enum class Position(

    /**
     * Русское название позиции
     */
    val russianName: String
) {

    /**
     * Вратарь
     */
    GOALKEEPER("Вратарь"),

    /**
     * Защитник
     */
    DEFENDER("Защитник"),

    /**
     * Полузащитник
     */
    MIDFIELD("Полузащитник"),

    /**
     * Нападающий
     */
    FORWARD("Нападающий")
}