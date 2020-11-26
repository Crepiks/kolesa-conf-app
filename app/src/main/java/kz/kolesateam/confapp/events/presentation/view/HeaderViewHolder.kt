package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R

const val GREETING_FORMAT = "Привет, %s"

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val greeting: TextView = view.findViewById(R.id.item_upcoming_event_header_greeting)

    fun bind(userName: String) {
        if (userName.isNotEmpty()) {
            greeting.text = GREETING_FORMAT.format(userName)
        }
    }
}