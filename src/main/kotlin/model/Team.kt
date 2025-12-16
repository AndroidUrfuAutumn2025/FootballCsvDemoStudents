package model

data class Team(
    val name: String,
    val players: MutableList<Player> = mutableListOf()
) {
    // Подсчет среднего числа удалений
    fun getAverageRedCards(): Double {
        if (players.isEmpty()) return 0.0
        val totalReds = players.sumOf { it.redCards }
        return totalReds.toDouble() / players.size
    }
}