package com.finalgriever.mayi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var playerOneName : EditText? = null
    var playerTwoName : EditText? = null
    var playerThreeName : EditText? = null
    var playerFourName : EditText? = null
    var playerFiveName : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        playerOneName = findViewById(R.id.playerOneNameText)
        playerTwoName = findViewById(R.id.playerTwoNameText)
        playerThreeName = findViewById(R.id.playerThreeNameText)
        playerFourName = findViewById(R.id.playerFourNameText)
        playerFiveName = findViewById(R.id.playerFiveNameText)

        startGameButton.setOnClickListener {
            val errors = validatePlayerNames()
            if (errors.size > 0) {

            }
            startActivity(Intent(this@MainActivity, GameActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun validatePlayerNames(): List<String> {
        val errors = mutableListOf<String>()
        var playerOneNameString = playerOneName?.text.toString()
        var playerTwoNameString = playerTwoName?.text.toString()

        if (playerOneNameString === "" && playerTwoNameString === "") {
            errors.add("There must be at least two players")
        }

        return errors
    }
}
