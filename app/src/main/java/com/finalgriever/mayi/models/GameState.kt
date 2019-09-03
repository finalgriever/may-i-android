package com.finalgriever.mayi.models

data class Round(val objective: String, val scores: MutableMap<Int, Int> = mutableMapOf())
data class Player(val name: String, val number: Int)

object GameState {
    public val NO_SCORE = -1
    private val roundObjectives = listOf("Two Triples", "One Triple and One Straight", "Two Straights", "Three Triples", "Two Triples and One Straight", "One Triple and Two Straights", "Three Straights")
    private val rounds = mutableListOf<Round>()
        get() = field
    private val players = mutableListOf<Player>()
        get() = field
    private var currentRound = 0
        get() = field
    private var gameFinished = false
        get() = field
    private var errors = mutableListOf<String>()
        get() = field

    init {
        startGame()
    }

    fun startGame() {
        currentRound = 0
        gameFinished = false
        errors.clear()
        players.clear()
        rounds.clear()
        for(objective in roundObjectives) {
            rounds.add(Round(objective))
        }
    }

    fun currentRoundName(): String {
        return rounds[currentRound].objective
    }

    fun addPlayer(playerName: String) {
        if (players.size >=5) return
        if (playerName == "") return
        players.add(Player(playerName, players.size));
    }

    fun getPlayerNames(): List<String> {
        var playerNames = mutableListOf<String>()
        for (player in players) {
            playerNames.add(player.name)
        }
        return playerNames
    }

    fun getCurrentRoundScores(): List<Int> {
        var scores = mutableListOf<Int>()
        for (index in 0 until players.size) {
            scores.add(rounds[currentRound]?.scores[index] ?: NO_SCORE)
        }
        return scores
    }

    fun setScore(playerNumber: Int, score: Int) {
        rounds[currentRound].scores[playerNumber] = score
    }

    fun nextRound() {
        validateRound()
        if (errors.size != 0) return
        if (currentRound >= 12) gameFinished = true
        else currentRound++
    }

    fun gameFinished() : Boolean {
        return gameFinished
    }

    fun validateRound() {
        val roundToValidate = rounds[currentRound]
        errors.clear()
        var zeroScoresFound = 0
        if (roundToValidate.scores.size != players.size) {
            errors.add("You have not entered a score for all players")
        }
        for (key in roundToValidate.scores.keys) {
            val score = roundToValidate.scores[key]
            if (score != 0 && (score!! % 5) != 0) {
                errors.add("Scores must be a multiple of 5")
            }
            if (score == 0) zeroScoresFound++
        }
        if (zeroScoresFound < 1) errors.add("At least one player must have a score of zero to proceed")
        if (zeroScoresFound > 1) errors.add("Only one player should have a score of zero")
        errors = errors.distinct().toMutableList()
    }

    fun totalScores(): List<Int> {
        val totals = mutableListOf<Int>()
        for (player in players) totals.add(0)
        for (round in rounds) {
            for (player in players) {
                totals[player.number] += round.scores[player.number] ?: 0
            }
        }
        return totals
    }

    fun gameErrors() : List<String> {
        return errors
    }
}