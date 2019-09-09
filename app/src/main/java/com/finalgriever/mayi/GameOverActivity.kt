package com.finalgriever.mayi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.finalgriever.mayi.models.GameState

import kotlinx.android.synthetic.main.activity_game_over.*
import android.content.Intent



class GameOverActivity : AppCompatActivity() {
    private val labels = mutableListOf<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        labels.addAll(listOf(winnerLabel, secondPlaceLabel, thirdPlaceLabel, fourthPlaceLabel, fifthPlaceLabel))
        connectListeners()
        updateUI()
    }

    private fun connectListeners() {

        restartGameButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun updateUI() {
        val finalScores = GameState.finalScores()
        for (index in finalScores.indices) {
            val position = positionAsNth(finalScores[index].position)
            labels[index].text = "$position: ${finalScores[index].name} with ${finalScores[index].score} points"
        }
        for (index in finalScores.size .. 4) {
            labels[index].text = ""
            labels[index].visibility = View.GONE
        }
    }

    private fun positionAsNth(position: Int): String {
        return when (position) {
            1 -> "1st"
            2 -> "2nd"
            3 -> "3rd"
            4 -> "4th"
            5 -> "5th"
            else -> ""
        }
    }
}
