package com.example.landscapelayout

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_SQUARE_SIZE = "com.example.landscapelayout.tap_the_square.SQUARE_SIZE"

class MainActivity : AppCompatActivity() {

    private lateinit var seekBar: SeekBar
    private lateinit var seekBarLabel: TextView
    private lateinit var showSquareButton: Button

    private val squareResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result -> handleSquareResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        seekBar = findViewById(R.id.seek_bar)
        seekBarLabel = findViewById(R.id.seek_bar_label)
        showSquareButton = findViewById(R.id.show_square_button)

        val initialProgress = seekBar.progress
        updateLabel(initialProgress)

        // TODO add listener to update label as seekbar changes.

        seekBar.setOnSeekBarChangeListener( object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbarComponent: SeekBar?, progress: Int, fromUser: Boolean) {
                updateLabel(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        showSquareButton.setOnClickListener {
            showSquare()
        }


    }

    private fun updateLabel(progress: Int) {
        seekBarLabel.text = getString(R.string.seekbar_value_message, progress)
    }

    private fun showSquare() {
        // launch the SquareActivity
        val showSquareIntent = Intent(this, SquareActivity::class.java)  // initializes showSquareIntent variable that holds an intent referencing the current MainActivity object instance of the android system to close MainActivity and find and run SquareActivity class
        showSquareIntent.putExtra(EXTRA_SQUARE_SIZE, seekBar.progress) // tell SquareActivity how large the square should be based on the progress of the Seekbar.
       // startActivity(showSquareIntent)  // calls activity method startActivity with intent
        squareResultLauncher.launch(showSquareIntent)

    /*    Intent(this, SquareActivity::class.java).apply {  // apply scope function
            putExtra(EXTRA_SQUARE_SIZE, seekBar.progress)
            squareResultLauncher.launch(this)
        }
    */
    }



    private fun handleSquareResult(result: ActivityResult) {
        // display result to user
        if (result.resultCode == RESULT_OK) {
            val intent = result.data  // gets the intent
            val tapped = intent?.getBooleanExtra(EXTRA_TAPPED_INSIDE_SQUARE, false) ?: false // intents are nullable types, this variable checks Boolean in value in result string, if intent is null returns false
            val message = if(tapped) {
                getString(R.string.tapped_square)
            } else {
                getString(R.string.missed_square)
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else if (result.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, getString(R.string.did_not_try), Toast.LENGTH_SHORT).show()
        }
    }


}