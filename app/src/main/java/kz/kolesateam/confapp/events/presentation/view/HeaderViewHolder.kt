package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R

class HeaderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val greeting: TextView = view.findViewById(R.id.item_upcoming_event_header_greeting)

    fun bind(userName: String) {
        if (userName.isNotEmpty()) {
            greeting.text = view.context.getString(R.string.item_upcoming_events_header_greeting_fmt, userName)
        }
    }
}