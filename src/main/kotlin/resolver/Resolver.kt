package resolver

import model.CSVParser
import java.io.InputStream

class Resolver{
    fun readCSVFile(inputStream: InputStream): List<List<String>> {
        return inputStream.bufferedReader().use { reader ->
            reader.readLines()
                .map { line -> line.split(";").map { it.trim() } }
        }
    }

    fun getCountWithoutAgency(): Int {
        val res = object{}.javaClass.getResourceAsStream("/fakePlayers.csv")
        if (res != null) {
            try {
                val players = CSVParser.parsePlayersFromCSV(res)
                val count = players.count { player ->
                    player.agency.isBlank() ||
                            player.agency.equals("null", ignoreCase = true)
                }

                // Дополнительная логика для вывода информации (опционально)
                players.forEach { player ->
                    if (player.agency.isBlank() || player.agency.equals("null", ignoreCase = true)) {
                        println("Игрок без агента: ${player.name} (Национальность: ${player.team})")
                    } else {
                        println("Игрок: ${player.name}, Национальность: ${player.team}, Агент: ${player.agency}")
                    }
                }

                return count
            } catch (e: Exception) {
                println("Ошибка при парсинге CSV: ${e.message}")
                return 0
            }
        } else {
            println("Файл fakePlayers.csv не найден в resources!")
            return 0
        }
    }

    // Выведите автора наибольшего числа голов из числа защитников и их количество.
    fun getBestScorerDefender(): Pair<String, Int> {
        val res = object{}.javaClass.getResourceAsStream("/fakePlayers.csv")
        val position = "DEFENDER"
        var max = 0
        var name = ""

        if (res != null) {
            val ag = readCSVFile(res)
            ag.forEach { parts ->
                // Пропускаем заголовок и проверяем наличие нужных полей
                if (parts[3].trim() == position) {
                    val goals = parts[8].toIntOrNull() ?: 0
                    if (goals >= max) {
                        max = goals
                        name = parts[0]
                    }
                }
            }
            return Pair(name, max)
        } else {
            println("Файл fakePlayers.csv не найден в resources!")
            return Pair("", 0)
        }
    }

    // Выведите русское название позиции самого дорогого немецкого игрока.
    fun getTheExpensiveGermanPlayerInfo(): String {
        val res = object{}.javaClass.getResourceAsStream("/fakePlayers.csv")

        var maxCost = 0
        var playerName = ""
        var englishPosition = ""

        val positionMap = mapOf(
            "GOALKEEPER" to "Вратарь",
            "DEFENDER" to "Защитник",
            "MIDFIELD" to "Полузащитник",
            "FORWARD" to "Нападающий"
        )

        readCSVFile(res).forEach { parts ->
            if (parts[0] == "Name") return@forEach

            val cost = parts[6].toIntOrNull() ?: 0
            val country = parts.getOrNull(4)?.trim() ?: ""

            if (country.equals("Germany", ignoreCase = true) && cost > maxCost) {
                maxCost = cost
                playerName = parts[0]
                englishPosition = if (parts.getOrNull(3) != null) {
                    parts[3].trim()
                } else {
                    ""
                }
            }
        }

        if (playerName.isNotEmpty()) {
            val russianPosition = if (positionMap.containsKey(englishPosition.toUpperCase())) {
                positionMap[englishPosition.toUpperCase()]
            } else {
                englishPosition
            }
            return "Игрок: " + playerName + ", Позиция: " + russianPosition + ", Стоимость: " + maxCost
        } else {
            return "Немецкие игроки не найдены"
        }
    }

    // Выберите команду с наибольшим числом удалений на одного игрока.
    fun getTheRudestTeam(): String {
        val res = object{}.javaClass.getResourceAsStream("/fakePlayers.csv")

        val teamRedCards = mutableMapOf<String, Int>()
        val teamPlayerCount = mutableMapOf<String, Int>()

        res.bufferedReader().use { reader ->
            var isFirstLine = true

            reader.forEachLine { line ->
                val parts = line.split(";").map { it.trim() }

                if (isFirstLine) {
                    isFirstLine = false
                    return@forEachLine
                }

                val team = parts[1]
                val redCards = parts[11].toIntOrNull() ?: 0

                teamRedCards[team] = teamRedCards.getOrDefault(team, 0) + redCards
                teamPlayerCount[team] = teamPlayerCount.getOrDefault(team, 0) + 1
            }
        }

        var rudestTeam = ""
        var maxAverageRedCards = 0.0

        teamRedCards.forEach { (team, totalRedCards) ->
            val playerCount = teamPlayerCount[team] ?: 1
            val averageRedCards = totalRedCards.toDouble() / playerCount

            if (averageRedCards > maxAverageRedCards) {
                maxAverageRedCards = averageRedCards
                rudestTeam = team
            }
        }

        return if (rudestTeam.isNotEmpty()) {
            rudestTeam
        } else {
            "Команды не найдены"
        }
    }
}