package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val userName: TextView = view.findViewById(R.id.item_upcoming_event_header_greeting)

    fun bind(name: String) {
        userName.text = name
    }
}