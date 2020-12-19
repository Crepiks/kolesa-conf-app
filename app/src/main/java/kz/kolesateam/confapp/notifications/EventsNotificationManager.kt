package kz.kolesateam.confapp.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.eventDetails.presentation.EventDetailsRouter

class EventsNotificationManager(
    private val context: Context
) {

    private var counter: Int = 0

    init {
        createNotificationIfNeeded()
    }

    fun sendNotification(
        eventId: Int,
        title: String,
        text: String
    ) {
        val notification: Notification = createNotification(
            eventId,
            title,
            text
        )
        val notificationId = counter++
        NotificationManagerCompat.from(context).notify(
            notificationId,
            notification
        )
    }

    private fun getChannelId(): String = context.packageName + ".events_notification_manager"

    private fun createNotification(
        eventId: Int,
        title: String,
        text: String
    ): Notification = NotificationCompat.Builder(context, getChannelId())
        .setSmallIcon(R.drawable.ic_favorite_fill)
        .setContentTitle(title)
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
        .setContentIntent(getPendingIntent(eventId))
        .build()

    private fun getPendingIntent(eventId: Int): PendingIntent {
        val eventDetailsIntent: Intent =
            EventDetailsRouter().createNotificationIntent(context, eventId).apply {
                flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
            }

        return PendingIntent.getActivity(
            context,
            0,
            eventDetailsIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
    }

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