package parser

import model.Player
import model.Team
import java.io.File

object CsvParser {
    fun parsePlayers(): List<Player> {
        val players = mutableListOf<Player>()

        // Получаем путь к файлу
        val file = File("src/main/resources/fakePlayers.csv")

        // Читаем все строки из файла
        val lines = file.readLines()

        // Пропускаем первую строку с заголовками и обрабатываем остальные
        for (i in 1 until lines.size) {
            val line = lines[i]
            val fields = line.split(";")

            if (fields.size >= 12) {
                try {
                    // Извлекаем данные из полей
                    val name = fields[0]
                    val teamName = fields[1]
                    val city = fields[2]
                    val position = fields[3]
                    val nationality = fields[4]
                    val agency = if (fields[5].isBlank()) null else fields[5]
                    val transferCost = fields[6].toDouble()
                    val matches = fields[7].toInt()
                    val goals = fields[8].toInt()
                    val assists = fields[9].toInt()
                    val yellowCards = fields[10].toInt()
                    val redCards = fields[11].toInt()

                    // Создаем объект команды
                    val team = Team(teamName, city)

                    // Создаем объект игрока
                    val player = Player(
                        name = name,
                        team = team,
                        position = position,
                        nationality = nationality,
                        agency = agency,
                        transferCost = transferCost,
                        matches = matches,
                        goals = goals,
                        assists = assists,
                        yellowCards = yellowCards,
                        redCards = redCards
                    )

                    players.add(player)
                } catch (e: Exception) {
                    println("Ошибка при обработке строки: $line")
                    e.printStackTrace()
                }
            }
        }

        return players
    }
}