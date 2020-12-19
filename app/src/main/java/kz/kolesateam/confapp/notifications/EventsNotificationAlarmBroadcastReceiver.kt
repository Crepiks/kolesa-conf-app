package kz.kolesateam.confapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.R

private const val DEFAULT_EVENT_ID = 0

class EventsNotificationAlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val eventId: Int =
            intent?.getIntExtra(EVENT_NOTIFICATION_ID_EXTRA_KEY, DEFAULT_EVENT_ID) ?: return
        val title: String = intent.getStringExtra(EVENT_NOTIFICATION_TEXT_EXTRA_KEY).orEmpty()
        context?.let {
            EventsNotificationManager(context).sendNotification(
                eventId,
                context.getString(R.string.notifications_events_notification_title),
                title
            )
        }
    }
}