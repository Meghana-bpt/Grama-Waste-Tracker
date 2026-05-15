package com.example.gramawastetracker

import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

class ReportActivity : AppCompatActivity() {

    private lateinit var imgPreview: ImageView
    private lateinit var tvLocation: TextView

    private val database =
        FirebaseDatabase.getInstance()

    private val cameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap: Bitmap? ->

            bitmap?.let {
                imgPreview.setImageBitmap(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
               super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_report)

        imgPreview = findViewById(R.id.imgPreview)
        tvLocation = findViewById(R.id.tvLocation)

        val btnSubmit =
            findViewById<Button>(R.id.btnSubmit)

        val etDescription =
            findViewById<EditText>(R.id.etDescription)

        val btnCamera = findViewById<Button>(R.id.btnCamera)
        val btnLocation = findViewById<Button>(R.id.btnLocation)

        btnCamera.setOnClickListener {

            cameraLauncher.launch(null)
        }

        btnLocation.setOnClickListener {

            getLocation()
        }

        btnSubmit.setOnClickListener {

            val description =
                etDescription.text.toString()

            val report =
                hashMapOf(

                    "description" to description,

                    "location" to
                            tvLocation.text.toString(),

                    "timestamp" to
                            System.currentTimeMillis()
                )

            database.reference
                .child("reports")
                .push()
                .setValue(report)
        }
    }

    private fun getLocation() {

        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)

        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                100
            )

            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->

                location?.let {

                    tvLocation.text =
                        "Lat: ${it.latitude}, Lng: ${it.longitude}"
                }
            }
    }
}