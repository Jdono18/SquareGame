package com.example.landscapelayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_TAPPED_INSIDE_SQUARE = "com.example.landscapelayout.TAPPED_INSIDE_SQUARE"


class SquareActivity : AppCompatActivity() {

    private lateinit var squareImage: ImageView
    private lateinit var container: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square)

        squareImage = findViewById(R.id.square)
        container = findViewById(R.id.container)

        var squareSize = intent.getIntExtra(EXTRA_SQUARE_SIZE, 100)

        if (squareSize == 0) {
            squareSize = 1
        }
        squareImage.layoutParams.width = squareSize * 5
        squareImage.layoutParams.height = squareSize * 5

        squareImage.setOnClickListener {
            squareTapped(true)
        }

        container.setOnClickListener {
            squareTapped(false)
        }

    }

    private fun squareTapped(didTapSquare: Boolean) {
        // sends a message back to main activity, end square activity
        //Toast.makeText(this, "square tapped $didTapSquare", Toast.LENGTH_SHORT).show()
        val resultIntent = Intent()  // initializes variable to hold an intent
        resultIntent.putExtra(EXTRA_TAPPED_INSIDE_SQUARE, didTapSquare)  // packages resultIntent with key value pair as an extra
        setResult(RESULT_OK, resultIntent) // sets the result and passes the data back to MainActivity
        finish()  // ends SquareActivity

    /*    Intent().apply {
            putExtra(EXTRA_TAPPED_INSIDE_SQUARE, didTapSquare)
            setResult(RESULT_OK, this)
            finish()
        }
    */

    }
}