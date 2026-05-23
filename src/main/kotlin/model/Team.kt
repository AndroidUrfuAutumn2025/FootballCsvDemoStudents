package model

data class Team(
    val name: String,
    val city: String,
    val players: List<Player> = emptyList()
) {
    val averageRedCardsPerPlayer: Double
        get() = if (players.isEmpty()) 0.0 else players.sumOf { it.redCards }.toDouble() / players.size

    val totalTransferValue: Long
        get() = players.sumOf { it.transferCost }
}