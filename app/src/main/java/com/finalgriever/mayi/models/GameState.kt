package com.finalgriever.mayi.models

data class Round(val objective: String, val scores: MutableMap<Int, Int> = mutableMapOf())
data class Player(val name: String, val number: Int)

class GameState() {
    private val roundObjectives = listOf("Two Triples", "One Triple and One Straight", "Two Straights", "Three Triples", "Two Triples and One Straight", "One Triple and Two Straights", "Three Straights")
    private val rounds = mutableListOf<Round>()
        get() = field
    private val players = mutableListOf<Player>()
        get() = field
    private var currentRound = 0
        get() = field
    private var gameFinished = false
        get() = field

    init {
        startGame()
    }

    fun startGame() {
        currentRound = 0
        gameFinished = false
        rounds.clear()
        for(objective in roundObjectives) {
            rounds.add(Round(objective))
        }
    }

    fun addPlayer(playerName: String) {
        if (players.size >=5) return
        if (playerName == "") return
        players.add(Player(playerName, players.size));
    }

    fun setScore(playerNumber: Int, score: Int) {
        rounds[currentRound].scores[playerNumber] = score
    }

    fun nextRound() {
        if (currentRound >= 12) gameFinished = true
        currentRound++
    }

    fun isFinalRound(): Boolean {
        return currentRound == 12
    }

    fun totalScores(): Map<Int, Int> {
        val totals = mutableMapOf<Int, Int>()
        for (round in rounds) {
            for (player in players) {
                if (!round.scores.containsKey(player.number)) continue
                totals[player.number] = if (
                    totals.containsKey(player.number) && totals[player.number] != null) {
                    round.scores[player.number]!! + totals[player.number]!!
                } else {
                    0
                }
            }
        }
        return totals
    }
}