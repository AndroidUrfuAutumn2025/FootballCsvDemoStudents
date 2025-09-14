import model.Player
import model.Team
import csv.parser.CsvParser

class PlayerCsvParser : CsvParser<Player>(
    headers = mapOf(
        "Name" to 0,
        "Team" to 1,
        "City" to 2,
        "Position" to 3,
        "Nationality" to 4,
        "Agency" to 5,
        "Transfer cost" to 6,
        "Participations" to 7,
        "Goals" to 8,
        "Assists" to 9,
        "Yellow cards" to 10,
        "Red cards" to 11
    )
) {
    override fun parse(line: String): Player {
        val values = line.split(";")

        return Player(
            name = getValue("Name", values),
            team = Team(
                name = getValue("Team", values),
                city = getValue("City", values)
            ),
            position = getValue("Position", values),
            nationality = getValue("Nationality", values),
            agency = getNullableString("Agency", values),
            transferCost = getLongValue("Transfer cost", values),
            participations = getIntValue("Participations", values),
            goals = getIntValue("Goals", values),
            assists = getIntValue("Assists", values),
            yellowCards = getIntValue("Yellow cards", values),
            redCards = getIntValue("Red cards", values)
        )
    }
}