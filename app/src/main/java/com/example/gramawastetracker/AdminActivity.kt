package com.example.gramawastetracker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.database.*

class AdminActivity : AppCompatActivity() {

    private lateinit var listReports: ListView

    private val reportList =
        ArrayList<String>()

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        setContentView(
            R.layout.activity_admin
        )


        listReports =
            findViewById(
                R.id.listReports
            )


        val adapter =
            ArrayAdapter(

                this,

                android.R.layout
                    .simple_list_item_activated_1,

                reportList
            )


        listReports.adapter =
            adapter


        FirebaseDatabase
            .getInstance()

            .getReference(
                "reports"
            )

            .addValueEventListener(

                object : ValueEventListener {

                    override fun onDataChange(
                        snapshot: DataSnapshot
                    ) {

                        reportList.clear()


                        for (
                        report in snapshot.children
                        ) {

                            val description =

                                report.child(
                                    "description"
                                ).value.toString()


                            val location =

                                report.child(
                                    "location"
                                ).value.toString()


                            reportList.add(

                                "🗑 $description\n📍 $location"
                            )
                        }


                        adapter
                            .notifyDataSetChanged()
                    }


                    override fun onCancelled(
                        error: DatabaseError
                    ) {

                    }
                }
            )
    }
}