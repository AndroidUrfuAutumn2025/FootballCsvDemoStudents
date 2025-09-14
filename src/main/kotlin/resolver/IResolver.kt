package resolver

import model.Player
import model.Team

interface IResolver {
    fun countWithoutAgency(): Int
    fun bestDefenderByGoals(): Pair<String, Int>
    fun mostExpensiveGermanPosition(): String
    fun teamWithMostRedCards(): Team
    fun nationalityDistribution(): Map<String, Int>
}