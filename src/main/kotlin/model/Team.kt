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
     * Список игроков
     */
    val players: List<Player> = emptyList()
) {

    /**
     * Общее количество красных карточек команды
     */
    private val totalRedCards: Int
        get() = players.sumOf { it.redCards }

    /**
     * Среднее количество красных карточек на игрока
     */
    val averageRedCards: Double
        get() = if (players.isEmpty()) 0.0 else totalRedCards.toDouble() / players.size

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Team

        if (name != other.name) return false
        if (city != other.city) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + city.hashCode()
        return result
    }


}