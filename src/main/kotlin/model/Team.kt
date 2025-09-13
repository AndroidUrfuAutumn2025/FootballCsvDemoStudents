package model

data class Team(
    val name: String,
    val city: String,
    val players: List<Player> = emptyList()
) {
    val averageRedCards: Double
        get() = players.map { it.redCards }.average()

    companion object {
        fun extractTeamsFromPlayers(players: List<Player>): List<Team> {
            return players.groupBy { it.teamName to it.city }
                .map { (key, teammates) ->
                    Team(
                        name = key.first,
                        city = key.second,
                        players = teammates
                    )
                }
        }
    }
}