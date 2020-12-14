package kz.kolesateam.confapp.upcomingEvents.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.BaseViewHolder
import kz.kolesateam.confapp.upcomingEvents.data.models.EventApiData

const val TIME_AND_PLACE_FORMAT = "%s - %s • %s"

class EventViewHolder(
    view: View,
    private val onEventClick: (eventTitle: String) -> Unit
) : BaseViewHolder<EventApiData>(view) {

    private val container: View = view.findViewById(R.id.layout_event_card_container)
    private val placement: TextView = view.findViewById(R.id.layout_event_card_placement)
    private val speakerName: TextView = view.findViewById(R.id.layout_event_card_speaker_name)
    private val speakerPosition: TextView =
        view.findViewById(R.id.layout_event_card_speaker_position)
    private val title: TextView = view.findViewById(R.id.layout_event_card_title)
    private val favoriteButton: ImageView =
        view.findViewById(R.id.layout_event_card_favorite_button)

    private var liked: Boolean = false

    init {
        setListeners()
    }

    override fun bind(event: EventApiData) {
        placement.text = TIME_AND_PLACE_FORMAT.format(event.startTime, event.endTime, event.place)
        speakerName.text = event.speaker?.fullName
        speakerPosition.text = event.speaker?.job
        title.text = event.title
    }

    private fun setListeners() {
        container.setOnClickListener {
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