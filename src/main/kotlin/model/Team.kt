package model

import model.enums.TeamName
import model.enums.TownName

data class Team(
    val name: TeamName,
    val town: TownName,
)