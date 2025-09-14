package model

/**
 * Футбольная команда
 */
class Team(

    /**
     * Название команды
     */
    val name: String,

    /**
     * Город команды
     */
    val city: String,

    /**
     * Список игроков команды
     */
    val players: MutableList<Player> = mutableListOf()
) {

    /**
     * Общее количество красных карточек у всех игроков команды
     */
    val totalRedCards: Int
        get() = players.sumOf { it.redCards }

    /**
     * Среднее количество красных карточек на одного игрока
     */
    val averageRedCardsByOnePlayer: Double
        get() = if (players.isEmpty()) {
            0.0
        } else {
            totalRedCards.toDouble() / players.size
        }
}