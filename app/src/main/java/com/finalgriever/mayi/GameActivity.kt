package com.finalgriever.mayi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.finalgriever.mayi.models.GameState
import com.finalgriever.mayi.extensions.afterTextChanged

import kotlinx.android.synthetic.main.activity_game.*

/* Plan
    On score entry
    * Set the score on the game state

    On Next button
    * Check that the game state is valid
    * Increment the round number
    * Update UI
 */

class GameActivity : AppCompatActivity() {
    private val playerNameLabels = mutableListOf<TextView>()
    private val playerScoreEntries = mutableListOf<EditText>()
    private val playerTotalScoreLabels = mutableListOf<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        playerNameLabels.addAll(listOf(playerOneNameLabel, playerTwoNameLabel, playerThreeNameLabel, playerFourNameLabel, playerFiveNameLabel))
        playerScoreEntries.addAll(listOf(playerOneScoreText, playerTwoScoreText, playerThreeScoreText, playerFourScoreText, playerFiveScoreText))
        playerTotalScoreLabels.addAll(listOf(playerOneTotalLabel, playerTwoTotalLabel, playerThreeTotalLabel, playerFourTotalLabel, playerFiveTotalLabel))
        connectListeners()
        updateUI()
    }

    fun connectListeners() {
        for (index in playerScoreEntries.indices) {
            playerScoreEntries[index].afterTextChanged { newValue ->
                try {
                    GameState.setScore(index, newValue.toInt())
                }
                catch(e: Exception) {
                    // Do nothing
                }
            }
        }
        nextRoundButton.setOnClickListener {
            GameState.nextRound()
            updateUI()
        }
    }

    fun updateUI() {
        if (GameState.gameFinished()) {
            startActivity(Intent(this@GameActivity, GameOverActivity::class.java))
            return
        }
        roundObjectiveLabel.text = GameState.currentRoundName()
        updatePlayerNames()
        updatePlayerScores()
        updateTotalScores()
        updateErrors()
    }

    fun updatePlayerScores() {
        val currentScores = GameState.getCurrentRoundScores()
        for (index in currentScores.indices) {
            if (currentScores[index] == GameState.NO_SCORE) {
                playerScoreEntries[index].setText("")
                playerScoreEntries[index].visibility = View.VISIBLE
            } else {
                playerScoreEntries[index].setText(currentScores[index].toString())
                playerScoreEntries[index].visibility = View.VISIBLE
            }
        }
        for (index in currentScores.size .. 4) {
            playerScoreEntries[index].visibility = View.GONE
            playerScoreEntries[index].setText("")
        }
    }

    fun updatePlayerNames() {
        val playerNames = GameState.getPlayerNames()
        for (index in playerNames.indices) {
            playerNameLabels[index].text = playerNames[index]
        }
        for (index in playerNames.size .. 4) {
            playerNameLabels[index].visibility = View.GONE
            playerNameLabels[index].text = ""
        }
    }

    fun updateTotalScores() {
        val currentTotals = GameState.totalScores()
        for (index in currentTotals.indices) {
            playerTotalScoreLabels[index].text = currentTotals[index].toString()
        }
        for (index in currentTotals.size .. 4) {
            playerTotalScoreLabels[index].text = ""
            playerTotalScoreLabels[index].visibility = View.GONE
        }
    }

    fun updateErrors() {
        val errors = GameState.gameErrors()
        if (errors.isEmpty()) {
            errorTextView.setText("")
            errorTextView.visibility = View.GONE
            return
        }

        errorTextView.text = errors.joinToString("\n")
        errorTextView.visibility = View.VISIBLE
    }
}