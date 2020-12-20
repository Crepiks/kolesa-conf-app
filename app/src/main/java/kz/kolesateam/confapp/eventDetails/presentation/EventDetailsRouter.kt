package kz.kolesateam.confapp.eventDetails.presentation

import android.content.Context
import android.content.Intent

const val EVENT_ID_EXTRA_KEY = "event_id"

class EventDetailsRouter() {

    fun createIntent(
        context: Context,
        eventId: Int
    ): Intent = Intent(context, EventDetailsActivity::class.java).apply {
        putExtra(EVENT_ID_EXTRA_KEY, eventId)
    }
}