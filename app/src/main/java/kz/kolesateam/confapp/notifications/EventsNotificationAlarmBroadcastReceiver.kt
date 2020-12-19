package kz.kolesateam.confapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

private const val NOTIFICATION_TITLE = "Скоро начнется доклад"
private const val DEFAULT_EVENT_ID = 0

class EventsNotificationAlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val eventId: Int =
            intent?.getIntExtra(EVENT_NOTIFICATION_ID_EXTRA_KEY, DEFAULT_EVENT_ID) ?: return
        val title: String = intent?.getStringExtra(EVENT_NOTIFICATION_TEXT_EXTRA_KEY).orEmpty()
        context?.let {
            EventsNotificationManager(context).sendNotification(eventId, NOTIFICATION_TITLE, title)
        }
    }
}