package kg.unicapp.weatherapi.fcm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kg.unicapp.weatherapi.R
import kg.unicapp.weatherapi.ui.MainActivity


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseService: FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("Extra", remoteMessage.data["body"])
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = "myChannel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "default", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
        val notification = Notification.Builder(this, channelId)
            .setContentTitle(remoteMessage.data["title"])
            .setContentText(remoteMessage.data["body"])
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_drop)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)



    }

}