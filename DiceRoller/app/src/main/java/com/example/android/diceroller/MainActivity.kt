package com.example.android.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

const val KEY_DICE1 = "key_dice1"
const val KEY_DICE2 = "key_dice2"

class MainActivity : AppCompatActivity() {

    private lateinit var diceImage : ImageView
    private lateinit var diceImage2 : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diceImage = findViewById(R.id.dice_image)
        diceImage2 = findViewById(R.id.dice_image2)

        if (savedInstanceState != null) {
            diceImage.setImageResource(savedInstanceState.getInt(KEY_DICE1, 1))
            diceImage2.setImageResource(savedInstanceState.getInt(KEY_DICE2, 1))
        }

        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.setOnClickListener { rollDice() }

        val resetButton: Button = findViewById(R.id.reset_button)
        resetButton.setOnClickListener { reset() }
        /*
        val countUpButton: Button = findViewById(R.id.count_up_button)
        countUpButton.setOnClickListener { countUP() }*/
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(KEY_DICE1, getRandomDiceImage())
        outState.putInt(KEY_DICE2, getRandomDiceImage())
    }

    private fun getRandomDiceImage() : Int {
        return when ((1..6).random()) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    private fun rollDice() {
        /*Toast.makeText(this, "Rices rolled",
            Toast.LENGTH_SHORT).show()*/

        val d = getRandomDiceImage()
        var d2 = getRandomDiceImage()

        while (d == d2) {
            d2 = getRandomDiceImage()
        }

        diceImage.setImageResource(d)
        diceImage2.setImageResource(d2)
    }

    private fun reset() {
        val drawableResource = R.drawable.empty_dice
        diceImage.setImageResource(drawableResource)
        diceImage2.setImageResource(drawableResource)
    }
    /*
    private fun countUP() {
        val resultText: TextView = findViewById(R.id.result_text)
        val text: String = resultText.text.toString()

        if (text == "Welcome!") {
            resultText.text = "1"
        }
        else {
            var result: Int = text.toInt()

            if (result != 6) {
                result++
                resultText.text = result.toString()
            }
        }
    }*/
}
