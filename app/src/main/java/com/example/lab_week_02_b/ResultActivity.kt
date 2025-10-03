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
        private const val ERROR_KEY = "ERROR_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val colorCode = intent?.getStringExtra(COLOR_KEY)
        val backgroundScreen = findViewById<ConstraintLayout>(R.id.background_screen)
        val resultMessage = findViewById<TextView>(R.id.color_code_result_message)

        // Kalau null/kosong, langsung kirim error balik
        if (colorCode.isNullOrBlank()) {
            setResult(RESULT_OK, Intent().putExtra(ERROR_KEY, true))
            finish()
            return
        }

        try {
            backgroundScreen.setBackgroundColor(Color.parseColor("#$colorCode"))
        } catch (_: IllegalArgumentException) {
            // Kode warna tidak valid → kirim error ke MainActivity lalu tutup
            setResult(RESULT_OK, Intent().putExtra(ERROR_KEY, true))
            finish()
            return
        }

        // Sampai sini berarti sukses
        resultMessage.text = getString(
            R.string.color_code_result_message,
            colorCode.uppercase()
        )
    }
}