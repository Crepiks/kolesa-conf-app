package kz.kolesateam.confapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import org.threeten.bp.ZonedDateTime

const val EVENT_NOTIFICATION_ID_EXTRA_KEY = "event_notification_id_extra_key"
const val EVENT_NOTIFICATION_TEXT_EXTRA_KEY = "event_notification_text_extra_key"
const val MINUTES_TO_SUBTRACT: Long = 5

class EventsNotificationAlarm(
    private val application: Application
) {

    fun scheduleNotification(
        eventId: Int,
        title: String,
        startTime: String
    ) {
        val alarmManager: AlarmManager? =
            application.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val pendingIntent: PendingIntent =
            Intent(application, EventsNotificationAlarmBroadcastReceiver::class.java).apply {
                putExtra(EVENT_NOTIFICATION_ID_EXTRA_KEY, eventId)
                putExtra(EVENT_NOTIFICATION_TEXT_EXTRA_KEY, title)
            }.let {
                PendingIntent.getBroadcast(application, eventId, it, PendingIntent.FLAG_ONE_SHOT)
            }

        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            getNotificationTimestamp(startTime),
            pendingIntent
        )
    }

    fun cancelNotification(
        eventId: Int
    ) {
        val alarmManager: AlarmManager? =
            application.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        val pendingIntent: PendingIntent =
            Intent(application, EventsNotificationAlarmBroadcastReceiver::class.java).let {
                PendingIntent.getBroadcast(
                    application,
                    eventId,
                    it,
                    PendingIntent.FLAG_ONE_SHOT
                )
            }

        alarmManager?.cancel(pendingIntent)
    }

    private fun getNotificationTimestamp(startTime: String): Long {
        val parsedDateTime: ZonedDateTime = ZonedDateTime.parse(startTime)
        val notificationTime: ZonedDateTime = parsedDateTime.minusMinutes(MINUTES_TO_SUBTRACT)
        return notificationTime.toEpochSecond() * 1000
    }
}