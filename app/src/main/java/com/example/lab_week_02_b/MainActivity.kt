package com.example.lab_week_02_b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    companion object {
        private const val COLOR_KEY = "COLOR_KEY"
    }

    private val submitButton: Button
        get() = findViewById(R.id.submit_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitButton.setOnClickListener {
            val colorCode =
                findViewById<TextInputEditText>(R.id.color_code_input_field).text.toString()

            if (colorCode.isNotEmpty()) {
                if (colorCode.length < 6) {
                    Toast.makeText(
                        this,
                        getString(R.string.color_code_input_wrong_length),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val resultIntent = Intent(this, ResultActivity::class.java)
                    resultIntent.putExtra(COLOR_KEY, colorCode)
                    startActivity(resultIntent)
                }
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.color_code_input_empty),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}