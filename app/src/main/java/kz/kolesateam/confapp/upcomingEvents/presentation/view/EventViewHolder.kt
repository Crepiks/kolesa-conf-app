package kz.kolesateam.confapp.upcomingEvents.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.BaseViewHolder
import kz.kolesateam.confapp.common.models.EventData
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

const val TIME_AND_PLACE_FORMAT = "%s - %s • %s"

class EventViewHolder(
    view: View,
    private val onEventClick: (eventId: Int) -> Unit,
    private val onFavoriteClick: (event: EventData, isFavorite: Boolean) -> Unit
) : BaseViewHolder<EventData>(view) {

    private val container: View = view.findViewById(R.id.layout_event_card_container)
    private val placement: TextView = view.findViewById(R.id.layout_event_card_placement)
    private val speakerName: TextView = view.findViewById(R.id.layout_event_card_speaker_name)
    private val speakerPosition: TextView =
        view.findViewById(R.id.layout_event_card_speaker_position)
    private val title: TextView = view.findViewById(R.id.layout_event_card_title)
    private val favoriteButton: ImageView =
        view.findViewById(R.id.layout_event_card_favorite_button)

    override fun bind(event: EventData) {
        placement.text = TIME_AND_PLACE_FORMAT.format(
            getHours(event.schedule.startTime),
            getHours(event.schedule.endTime),
            event.place
        )
        speakerName.text = event.speaker?.fullName
        speakerPosition.text = event.speaker?.job
        title.text = event.title

        if (event.isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_fill)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border)
        }

        setListeners(event)
    }

    private fun setListeners(event: EventData) {
        container.setOnClickListener {
            onEventClick(event.id)
        }

        favoriteButton.setOnClickListener {
            onFavoriteClick(event, !event.isFavorite)
        }
    }

    private fun getHours(dateTimeString: String): String {
        val parsedDateTime: ZonedDateTime = ZonedDateTime.parse(dateTimeString)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return parsedDateTime.format(formatter)
    }
}