package model

import org.apache.commons.lang3.ObjectUtils

class Player(data: List<String>) {
    val name = data[0]
    val team = data[1]
    val city = data[2]
    val position = data[3]
    val nationality = data[4]
    val agency= data[5]

    val transferCost = data[6].toIntOrNull() ?: 0
    val participations = data[7].toIntOrNull() ?: 0
    val goals = data[8].toIntOrNull() ?: 0
    val assists = data[9].toIntOrNull() ?: 0
    val yellowCards = data[10].toIntOrNull() ?: 0
    val redCards = data[11].toIntOrNull() ?: 0
}
