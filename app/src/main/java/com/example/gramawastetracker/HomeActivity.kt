package com.example.gramawastetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener {

                if (it.isSuccessful) {

                    Log.d(
                        "FCM_TOKEN",
                        it.result
                    )
                }
            }

        val btnTrack = findViewById<Button>(R.id.btnTrack)
        val btnReport = findViewById<Button>(R.id.btnReport)
        val btnAI = findViewById<Button>(R.id.btnAI)
        val btnAdmin =
            findViewById<Button>(
                R.id.btnAdmin
            )

        btnTrack.setOnClickListener {

            startActivity(
                Intent(this, TrackActivity::class.java)
            )
        }

        btnReport.setOnClickListener {

            startActivity(
                Intent(this, ReportActivity::class.java)
            )
        }

        btnAI.setOnClickListener {

            startActivity(
                Intent(this, AIActivity::class.java)
            )
        }

        btnAdmin.setOnClickListener {

            startActivity(

                Intent(
                    this,
                    AdminActivity::class.java
                )
            )
        }
    }
}