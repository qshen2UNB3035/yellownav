package ca.unb.mobiledev.yellownav

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationUtils(context:Context) {

    private var mContext = context
    private lateinit var notificationBuilder: NotificationCompat.Builder
    val notificationManager = NotificationManagerCompat.from(mContext)
    private val CHANNEL_ID = "My_Notification_Channel"


    init {
        createNotificationChannel()
        initNotificationBuilder()
    }


    fun launchNotification(){
        with(NotificationManagerCompat.from(mContext)) {
            notificationManager.notify(0, notificationBuilder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notifiManager: NotificationManager =
                mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notifiManager.createNotificationChannel(channel)
        }
    }


    private fun initNotificationBuilder() {


        val sampleIntent = Intent(mContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(mContext, 0, sampleIntent, 0)

        notificationBuilder = NotificationCompat.Builder(mContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Yellow Nav")
            .setContentText("Bus Tracking Available")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

}