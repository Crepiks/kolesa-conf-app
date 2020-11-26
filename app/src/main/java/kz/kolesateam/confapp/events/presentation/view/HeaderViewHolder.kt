package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val greeting: TextView = view.findViewById(R.id.item_upcoming_event_header_greeting)

    fun bind(text: String) {
        greeting.text = text
    }
}