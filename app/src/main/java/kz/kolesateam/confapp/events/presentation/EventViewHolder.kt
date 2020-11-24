package kz.kolesateam.confapp.events.presentation

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.EventApiData

private const val TAG = "EventViewHolder"

class EventViewHolder(
        view: View,
        private val onEventClick: (eventTitle: String) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val container: View = view.findViewById(R.id.item_event_card_container)
    private val placement: TextView = view.findViewById(R.id.item_event_card_placement)
    private val speakerName: TextView = view.findViewById(R.id.item_event_card_speaker_name)
    private val speakerPosition: TextView = view.findViewById(R.id.item_event_card_speaker_position)
    private val title: TextView = view.findViewById(R.id.item_event_card_title)

    init {
        setListeners()
    }

    fun bind(event: EventApiData) {
        placement.text = event.place
        speakerName.text = event.speaker?.fullName
        speakerPosition.text = event.speaker?.job
        title.text = event.title
    }

    private fun setListeners() {
        container.setOnClickListener{
            Log.d(TAG, "Clicked")
            val eventTitle = title.text.toString()
            onEventClick(eventTitle)
        }
    }
}