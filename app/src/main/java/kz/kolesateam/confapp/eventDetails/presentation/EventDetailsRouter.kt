package kz.kolesateam.confapp.eventDetails.presentation

import android.content.Context
import android.content.Intent

const val EVENT_ID_EXTRA_KEY = "event_id"

class EventDetailsRouter() {

    private fun createIntent(
        context: Context
    ): Intent = Intent(context, EventDetailsActivity::class.java)

    fun createNotificationIntent(
        context: Context,
        eventId: Int
    ): Intent = createIntent(context).apply {
        putExtra(EVENT_ID_EXTRA_KEY, eventId)
    }
}