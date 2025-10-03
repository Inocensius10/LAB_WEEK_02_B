package com.example.lab_week_02_b

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class ResultActivity : AppCompatActivity() {

    companion object {
        private const val COLOR_KEY = "COLOR_KEY"
        private const val ERROR_KEY = "ERROR_MESSAGE"
        private val HEX6 = Regex("^[0-9A-Fa-f]{6}$")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val colorCode = intent?.getStringExtra(COLOR_KEY)
        val backgroundScreen = findViewById<ConstraintLayout>(R.id.background_screen)
        val resultMessage = findViewById<TextView>(R.id.color_code_result_message)

        // Validasi dulu biar tidak crash
        if (colorCode.isNullOrBlank()) {
            setResult(
                RESULT_CANCELED,
                Intent().putExtra(ERROR_KEY, getString(R.string.color_code_input_empty))
            )
            finish()
            return
        }

        if (!HEX6.matches(colorCode)) {
            setResult(
                RESULT_CANCELED,
                Intent().putExtra(ERROR_KEY, getString(R.string.color_code_input_invalid))
            )
            finish()
            return
        }

        // Valid → terapkan warna & kirim OK
        val hex = "#${colorCode}"
        try {
            backgroundScreen.setBackgroundColor(Color.parseColor(hex))
            resultMessage.text = getString(
                R.string.color_code_result_message,
                colorCode.uppercase()
            )
            setResult(
                RESULT_OK,
                Intent().putExtra(COLOR_KEY, colorCode)
            )
        } catch (_: IllegalArgumentException) {
            // Jaga-jaga kalau parse masih ngaco
            setResult(
                RESULT_CANCELED,
                Intent().putExtra(ERROR_KEY, getString(R.string.color_code_input_invalid))
            )
        }
        // Tidak perlu finish() paksa; biarkan user lihat hasil. Tapi kalau maunya langsung balik, panggil finish().
    }
}