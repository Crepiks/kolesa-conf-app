package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.EventApiData

class EventViewHolder(
        view: View,
        private val onEventClick: (eventTitle: String) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val container: View = view.findViewById(R.id.item_event_card_container)
    private val placement: TextView = view.findViewById(R.id.item_event_card_placement)
    private val speakerName: TextView = view.findViewById(R.id.item_event_card_speaker_name)
    private val speakerPosition: TextView = view.findViewById(R.id.item_event_card_speaker_position)
    private val title: TextView = view.findViewById(R.id.item_event_card_title)
    private val favoriteButton: ImageView = view.findViewById(R.id.item_event_card_favorite_button)

    private var liked: Boolean = false

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
            val eventTitle = title.text.toString()
            onEventClick(eventTitle)
        }

        favoriteButton.setOnClickListener {
            liked = if (liked) {
                favoriteButton.setImageResource(R.drawable.ic_favorite_border)
                false
            } else {
                favoriteButton.setImageResource(R.drawable.ic_favorite_fill)
                true
            }
        }
    }
}