package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.HeaderItem
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem

class HeaderViewHolder(val view: View) : BaseViewHolder<UpcomingEventListItem>(view) {

    private val greeting: TextView = view.findViewById(R.id.item_upcoming_event_header_greeting)

    override fun bind(data: UpcomingEventListItem) {
        val userName: String = (data as? HeaderItem)?.userName ?: return
        if (userName.isNotEmpty()) {
            greeting.text = view.context.getString(R.string.item_upcoming_events_header_greeting_fmt, userName)
        }
    }
}