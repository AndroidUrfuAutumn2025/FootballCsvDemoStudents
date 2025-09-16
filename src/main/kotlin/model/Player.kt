package model

data class Player (
            val name : String,
            val team : String,
            val city : String,
            val position : String,
            val nationality : String,
            val agency : String,
            val cost : Int,
            val participation : Int,
            val goalCount : Int,
            val assistCount : Int,
            val yellowCardCount : Int,
            val redCardCount : Int
            )
