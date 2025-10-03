package com.example.lab_week_02_b

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class ResultActivity : AppCompatActivity() {

    companion object {
        private const val COLOR_KEY = "COLOR_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        if (intent != null) {
            val colorCode = intent.getStringExtra(COLOR_KEY)

            val backgroundScreen =
                findViewById<ConstraintLayout>(R.id.background_screen)
            val resultMessage =
                findViewById<TextView>(R.id.color_code_result_message)

            // Versi persis instruksi + sedikit pengaman supaya tidak crash
            if (!colorCode.isNullOrBlank()) {
                try {
                    backgroundScreen.setBackgroundColor(Color.parseColor("#$colorCode"))
                    resultMessage.text = getString(
                        R.string.color_code_result_message,
                        colorCode.uppercase()
                    )
                } catch (_: IllegalArgumentException) {
                    // Jika kode warna invalid, biarkan background default dan tampilkan fallback teks.
                    resultMessage.text = getString(R.string.color_code_input_invalid)
                }
            } else {
                resultMessage.text = getString(R.string.color_code_input_empty)
            }
        }
    }
}