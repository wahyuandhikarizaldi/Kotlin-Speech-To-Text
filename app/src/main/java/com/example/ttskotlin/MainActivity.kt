package com.example.ttskotlin

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var gambarvoice: ImageButton

    private val voiceRecognitionResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val resultData = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                editText.setText(resultData?.get(0))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gambarvoice = findViewById(R.id.gambarvoice)
        editText = findViewById(R.id.editText)

        gambarvoice.setOnClickListener {
            setVoice()
        }
    }

    private fun setVoice() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Coba kamu ucapkan??")
        try {
            voiceRecognitionResult.launch(intent)

        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
