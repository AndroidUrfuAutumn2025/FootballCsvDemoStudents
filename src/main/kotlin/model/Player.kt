package model

class Player {
    var namePlayer: String
    var team: Team
    var position: String
    var nationality: String
    var agency: String
    var transferCost: Int
    var countMatches: Int
    var countGoals: Int
    var countAssists: Int
    var countYellowCards: Int
    var countRedCards: Int

    constructor(){
        this.namePlayer = ""
        this.team = Team("", "")
        this.position = ""
        this.nationality = ""
        this.agency = ""
        this.transferCost = 0
        this.countMatches = 0
        this.countGoals = 0
        this.countAssists = 0
        this.countYellowCards = 0
        this.countRedCards = 0
    }

    constructor(namePlayer: String,
                team: Team,
                position: String,
                nationality: String,
                agency: String,
                transferCost: Int,
                countMatches: Int,
                countGoals: Int,
                countAssists: Int,
                countYellowCards: Int,
                countRedCards: Int){
        this.namePlayer = namePlayer
        this.team = team
        this.position = position
        this.nationality = nationality
        this.agency = agency
        this.transferCost = transferCost
        this.countMatches = countMatches
        this.countGoals = countGoals
        this.countAssists = countAssists
        this.countYellowCards = countYellowCards
        this.countRedCards = countRedCards
    }

    override fun toString(): String {
        return "Player(namePlayer='$namePlayer', team=$team, position='$position', nationality='$nationality', agency='$agency', transferCost=$transferCost, countMatches=$countMatches, countGoals=$countGoals, countAssists=$countAssists, countYellowCards=$countYellowCards, countRedCards=$countRedCards)"
    }
}


