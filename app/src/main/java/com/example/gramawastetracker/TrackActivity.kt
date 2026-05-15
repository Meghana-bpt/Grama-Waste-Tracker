package com.example.gramawastetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import com.google.firebase.database.*

class TrackActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var database: DatabaseReference

    private var truckMarker: Marker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_track)

        database =
            FirebaseDatabase.getInstance()
                .getReference("vehicleLocation")

        val mapFragment =
            supportFragmentManager
                .findFragmentById(
                    R.id.mapFragment
                ) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap


        database.addValueEventListener(

            object : ValueEventListener {

                override fun onDataChange(
                    snapshot: DataSnapshot
                ) {

                    val lat =
                        snapshot.child("latitude")
                            .getValue(Double::class.java)

                    val lng =
                        snapshot.child("longitude")
                            .getValue(Double::class.java)


                    if (lat != null && lng != null) {

                        val truckLocation =
                            LatLng(lat, lng)

                        truckMarker?.remove()

                        truckMarker =
                            mMap.addMarker(

                                MarkerOptions()
                                    .position(truckLocation)
                                    .title("Waste Vehicle")
                            )

                        mMap.animateCamera(

                            CameraUpdateFactory
                                .newLatLngZoom(
                                    truckLocation,
                                    15f
                                )
                        )
                    }
                }

                override fun onCancelled(
                    error: DatabaseError
                ) {

                }
            }
        )
    }
}