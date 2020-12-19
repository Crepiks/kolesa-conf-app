package kz.kolesateam.confapp.branchEvents.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.branchEvents.presentation.models.BranchEventListItem
import kz.kolesateam.confapp.branchEvents.presentation.models.EventItem
import kz.kolesateam.confapp.common.BaseViewHolder
import kz.kolesateam.confapp.events.domain.models.EventData

const val TIME_AND_PLACE_FORMAT = "%s - %s • %s"

class EventViewHolder(
    view: View,
    private val onFavoriteClick: (event: EventData, isFavorite: Boolean) -> Unit
) : BaseViewHolder<BranchEventListItem>(view) {

    private val placement: TextView = view.findViewById(R.id.layout_event_card_placement)
    private val speakerName: TextView = view.findViewById(R.id.layout_event_card_speaker_name)
    private val speakerPosition: TextView =
        view.findViewById(R.id.layout_event_card_speaker_position)
    private val title: TextView = view.findViewById(R.id.layout_event_card_title)
    private val favoriteButton: ImageView =
        view.findViewById(R.id.layout_event_card_favorite_button)

    override fun bind(data: BranchEventListItem) {
        val event: EventData = (data as? EventItem)?.data ?: return
        placement.text = TIME_AND_PLACE_FORMAT.format(
            event.schedule.startTime,
            event.schedule.endTime,
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
        favoriteButton.setOnClickListener {
            onFavoriteClick(event, !event.isFavorite)
        }
    }
}