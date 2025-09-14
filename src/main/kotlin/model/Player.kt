package model

/**
 * Игрок
 */
class Player(
    /**
     * Имя игрока
     */
    val name: String,

    /**
     * Команда игрока
     */
    val team: Team,

    /**
     * Позиция игрока
     */
    val position: String,

    /**
     * Национальность игрока
     */
    val nationality: String,

    /**
     * Агентство игрока
     */
    val agency: String?,

    /**
     * Стоимость трансфера
     */
    val transferCost: Long,

    /**
     * Количество участий в матчах
     */
    val participations: Int,

    /**
     * Количество голов
     */
    val goals: Int,

    /**
     * Количество голевых передач
     */
    val assists: Int,

    /**
     * Количество желтых карточек
     */
    val yellowCards: Int,

    /**
     * Количество красных карточек
     */
    val redCards: Int
)
