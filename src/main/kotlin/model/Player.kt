package model

/**
 * Модель игрока футбольной команды
 * name Имя игрока
 * team Название команды
 * city Город команды
 * position Позиция игрока
 * nationality Национальность игрока
 * agency Агентство игрока (может быть null)
 * transferCost Стоимость трансфера
 * participations Количество участий в матчах
 * goals Количество голов
 * assists Количество голевых передач
 * yellowCards Количество желтых карточек
 * redCards Количество красных карточек
 */
class Player(
    val name: String,
    val team: String,
    val city: String,
    val position: String,
    val nationality: String,
    val agency: String?,
    val transferCost: Long,
    val participations: Int,
    val goals: Int,
    val assists: Int,
    val yellowCards: Int,
    val redCards: Int
)
