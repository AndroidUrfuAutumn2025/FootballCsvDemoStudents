package model

import model.enums.Position

data class Player(
    val name: String,
    val team: Team,
    val position: Position,
    val nationality: String,
    val agency: String,
    val transferCost: Double,
    val participationCount: Int,
    val goalCount: Int,
    val assistsCount: Int,
    val yellowCardCount: Int,
    val redCardCount: Int) {
    init {
        require(transferCost >= 0) { "Цена должна быть положительна" }
        require(participationCount >= 0) { "Количество участий не может быть отрицательно" }
        require(goalCount >= 0) { "Количество голов не может быть отрицательно" }
        require(assistsCount >= 0) { "Количество помощи не может быть отрицательно" }
        require(yellowCardCount >= 0) { "Количество желтых карт не может быть отрицательно" }
        require(redCardCount >= 0) { "Количество красных карт не может быть отрицательно" }
    }
}