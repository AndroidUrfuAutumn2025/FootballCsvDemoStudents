package model

/**
 * Модель футбольной команды
 * name Название команды
 * city Город команды
 * players Список игроков команды
 * totalRedCards Общее количество красных карточек у всех игроков команды
 * averageRedCards Среднее количество красных карточек на игрока
 */
class Team(
    val name: String,
    val city: String,
    val players: List<Player> = emptyList()
) {
    val totalRedCards: Int get() = players.sumOf { it.redCards }
    val averageRedCards: Double get() = if (players.isEmpty()) 0.0 else totalRedCards.toDouble() / players.size
}
