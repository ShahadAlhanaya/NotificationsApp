package com.example.notificationsapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var editText: EditText
    lateinit var showButton: Button

    private val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }
    private val channelId = "notificationsapp.notifications"
    private val description = "Notification App Example"
    private val notificationId = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editText = findViewById(R.id.edt_typeHere)
        showButton = findViewById(R.id.btn_show)

        showButton.setOnClickListener {
            if(editText.text.trim().isNotEmpty()){
                showNotification(editText.text.toString())
            }else{
                Toast.makeText(this,"enter something :D",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showNotification(text: String){

        lateinit var builder: Notification.Builder

        val intent = Intent(this, ViewNotificationActivity::class.java)
        intent.putExtra("notification", text)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_round_notifications_24)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_round_notifications_24))
                .setContentIntent(pendingIntent)
                .setContentTitle("Notification")
                .setContentText(text)
        }
        else {
            builder = Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_round_notifications_24)
                .setContentIntent(pendingIntent)
                .setContentTitle("Notification")
                .setContentText(text)
        }
        notificationManager.notify(notificationId, builder.build())
    }
}