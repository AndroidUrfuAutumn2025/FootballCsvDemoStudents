package model

data class Team(
    val name: String,
    val city: String,
    val members: List<Player> = arrayListOf()
) {
    fun addPlayer(player: Player) {
        this.copy(members = this.members + player);
    }
}
