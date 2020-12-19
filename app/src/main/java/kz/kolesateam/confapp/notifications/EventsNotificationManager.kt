package kz.kolesateam.confapp.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kz.kolesateam.confapp.R

class EventsNotificationManager(
    private val context: Context
) {

    private var counter: Int = 0

    init {
        createNotificationIfNeeded()
    }

    fun sendNotification(
        title: String,
        text: String
    ) {
        val notification: Notification = createNotification(
            title = title,
            text = text
        )
        val notificationId = counter++
        NotificationManagerCompat.from(context).notify(
            notificationId,
            notification
        )
    }

    private fun getChannelId(): String = context.packageName + ".events_notification_manager"

    private fun createNotification(
        title: String,
        text: String
    ): Notification = NotificationCompat.Builder(context, getChannelId())
        .setSmallIcon(R.drawable.ic_favorite_fill)
        .setContentTitle(title)
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
        .build()

    private fun createNotificationIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val name = "ConfApplication"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
            getChannelId(),
            name,
            importance
        )
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}