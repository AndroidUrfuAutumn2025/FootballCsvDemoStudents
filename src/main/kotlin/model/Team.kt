package model

data class Team(
    val name: String,
    val city: String,
    val players: List<Player> = emptyList()
) {
    val totalTransferCost: Long
        get() = players.sumOf { it.transferCost }
    
    val averageRedCards: Double
        get() = if (players.isNotEmpty()) players.sumOf { it.redCards }.toDouble() / players.size else 0.0
}