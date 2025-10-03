package com.example.lab_week_02_b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    companion object {
        private const val COLOR_KEY = "COLOR_KEY"
        private const val ERROR_KEY = "ERROR_MESSAGE"
    }

    private val submitButton: Button
        get() = findViewById(R.id.submit_button)

    // Launcher modern pengganti startActivityForResult
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val inputWrapper = findViewById<TextInputLayout>(R.id.color_code_input_wraper)
        inputWrapper.error = null

        if (result.resultCode == RESULT_CANCELED) {
            // Ambil error dari ResultActivity
            val msg = result.data?.getStringExtra(ERROR_KEY)
            if (!msg.isNullOrBlank()) {
                inputWrapper.error = msg
            } else {
                inputWrapper.error = getString(R.string.color_code_input_invalid)
            }
        } else if (result.resultCode == RESULT_OK) {
            // Opsional: tampilkan toast sukses
            val applied = result.data?.getStringExtra(COLOR_KEY)?.uppercase()
            if (!applied.isNullOrBlank()) {
                Toast.makeText(
                    this,
                    getString(R.string.color_code_result_message, applied),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitButton.setOnClickListener {
            val colorCode =
                findViewById<TextInputEditText>(R.id.color_code_input_field).text.toString()

            when {
                colorCode.isEmpty() -> {
                    Toast.makeText(
                        this,
                        getString(R.string.color_code_input_empty),
                        Toast.LENGTH_LONG
                    ).show()
                }
                colorCode.length < 6 -> {
                    Toast.makeText(
                        this,
                        getString(R.string.color_code_input_wrong_length),
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    // Kirim ke ResultActivity via launcher (bukan startActivity)
                    val intent = Intent(this, ResultActivity::class.java)
                        .putExtra(COLOR_KEY, colorCode)
                    resultLauncher.launch(intent)
                }
            }
        }
    }
}