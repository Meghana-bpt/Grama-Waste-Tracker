package com.example.gramawastetracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

import androidx.core.app.NotificationCompat

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService :
    FirebaseMessagingService() {

    override fun onMessageReceived(
        message: RemoteMessage
    ) {

        val manager =
            getSystemService(
                NOTIFICATION_SERVICE
            ) as NotificationManager


        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            val channel =

                NotificationChannel(

                    "truck_channel",

                    "Truck Alerts",

                    NotificationManager.IMPORTANCE_HIGH
                )

            manager.createNotificationChannel(
                channel
            )
        }


        val notification =

            NotificationCompat.Builder(
                this,
                "truck_channel"
            )

                .setContentTitle(
                    "Grama Waste Tracker"
                )

                .setContentText(
                    message.notification?.body
                )

                .setSmallIcon(
                    R.mipmap.ic_launcher
                )

                .build()


        manager.notify(
            1,
            notification
        )
    }
}