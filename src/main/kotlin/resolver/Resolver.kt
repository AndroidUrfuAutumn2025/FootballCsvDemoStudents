package resolver

import model.Player
import model.Team
import org.jfree.data.general.DefaultPieDataset
import org.jfree.data.general.PieDataset
import parser.CsvParser

class Resolver : IResolver{

    val listPlayer: List<Player> = CsvParser.csvParser("src/main/resources/fakePlayers.csv")

    val englishToRussia = mapOf(
        "FORWARD" to "Нападающий",
        "DEFENDER" to "Защитник",
        "MIDFIELD" to "Полузащитник",
        "GOALKEEPER" to "Вратарь"
    )

    fun translate(word: String): String{
        return englishToRussia[word.uppercase()] ?: word
    }

    override fun getCountWithoutAgency(): Int {
        var count: Int = 0
        for (player in listPlayer){
            if (player.agency.isBlank()) {
                count += 1
            }
        }
        return count
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val playerD = listPlayer.filter { it.position == "DEFENDER" }
            .maxOf { it.countGoals }
        val playerL = listPlayer.filter { it.countGoals == playerD }
        return Pair(playerL[0].namePlayer, playerL[0].countGoals)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        var playerBigT: Player = Player()
        var transferCost: Int = 0
        for (player in listPlayer){
            if(transferCost < player.transferCost && player.nationality == "Germany"){
                transferCost = player.transferCost
                playerBigT = player
            }
        }
        return translate(playerBigT.position)
    }

    override fun getTheRudestTeam(): Team {
        val teamBest = mutableMapOf<Team, Double>()
        val playersByTeam = listPlayer.groupBy { it.team}
        for ((team, playersList) in playersByTeam){
            var countRedCard: Int = 0
            for (player in playersList) {
                countRedCard += player.countRedCards
            }
            val number = countRedCard.toDouble() / playersList.size
            teamBest[team] = number
        }
        val teamBestString = teamBest.maxByOrNull { it.value }?.key ?: Team("", "")
        return teamBestString
    }

    fun createDataset(): PieDataset<String> {
        val datasetListA = DefaultPieDataset<String>()
        val teamTransferCost = mutableMapOf<Team, Int>()
        val playersByTeam = listPlayer.groupBy { it.team}
        for ((team, playersList) in playersByTeam){
            var countTrans: Int = 0
            for (player in playersList) {
                countTrans += player.transferCost
            }
            teamTransferCost[team] = countTrans
        }
        teamTransferCost.forEach { (key, value) ->
            datasetListA.setValue(key.nameTeam, value.toDouble())
        }
        return datasetListA
    }

}