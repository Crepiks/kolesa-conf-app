package kz.kolesateam.confapp.events.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.EventApiData

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val placement: TextView = view.findViewById(R.id.item_event_card_placement)
    private val speakerName: TextView = view.findViewById(R.id.item_event_card_speaker_name)
    private val speakerPosition: TextView = view.findViewById(R.id.item_event_card_speaker_position)
    private val title: TextView = view.findViewById(R.id.item_event_card_title)

    fun bind(event: EventApiData) {
        placement.text = event.place
        speakerName.text = event.speaker?.fullName
        speakerPosition.text = event.speaker?.job
        title.text = event.title
    }
}