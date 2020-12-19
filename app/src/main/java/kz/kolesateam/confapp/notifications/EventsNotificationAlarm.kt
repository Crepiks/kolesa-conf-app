package kz.kolesateam.confapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

const val EVENT_NOTIFICATION_ID_EXTRA_KEY = "event_notification_id_extra_key"
const val EVENT_NOTIFICATION_TEXT_EXTRA_KEY = "event_notification_text_extra_key"

class EventsNotificationAlarm(
    private val application: Application
) {

    fun scheduleNotification(
        eventId: Int,
        title: String
    ) {
        val alarmManager: AlarmManager? =
            application.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val pendingIntent: PendingIntent =
            Intent(application, EventsNotificationAlarmBroadcastReceiver::class.java).apply {
                putExtra(EVENT_NOTIFICATION_ID_EXTRA_KEY, eventId)
                putExtra(EVENT_NOTIFICATION_TEXT_EXTRA_KEY, title)
            }.let {
                PendingIntent.getBroadcast(application, 0, it, 0)
            }

        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 5000,
            pendingIntent
        )
    }
}