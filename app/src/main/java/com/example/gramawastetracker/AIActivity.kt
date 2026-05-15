package com.example.gramawastetracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AIActivity : AppCompatActivity() {

    private lateinit var etPrompt: EditText
    private lateinit var tvResponse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ai)

        etPrompt = findViewById(R.id.etPrompt)
        tvResponse = findViewById(R.id.tvAIResponse)

        val btnAskAI = findViewById<Button>(R.id.btnAskAI)

        btnAskAI.setOnClickListener {

            askGemini()
        }
    }

    private fun askGemini() {

        val userPrompt =
            etPrompt.text.toString()

        if (userPrompt.isEmpty()) {

            tvResponse.text = "Please enter a question"

            return
        }

        tvResponse.text = "Thinking..."

        val generativeModel =
            GenerativeModel(
                modelName = "gemini-2.0-flash",
                apiKey = BuildConfig.GEMINI_API_KEY
            )

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response =
                    generativeModel.generateContent(
                        userPrompt
                    )

                withContext(
                    Dispatchers.Main
                ) {

                    tvResponse.text =
                        response.text ?: "No response"
                }

            } catch (e: Exception) {

                withContext(
                    Dispatchers.Main
                ) {

                    tvResponse.text =
                        e.message
                }
            }
        }
    }
}