package kz.kolesateam.confapp.upcomingEvents.presentation.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.domain.models.EventData

class EventListAdapter(
    private val onEventClick: (eventTitle: String) -> Unit,
    private val onFavoriteClick: (event: EventData, isFavorite: Boolean) -> Unit
) : RecyclerView.Adapter<EventViewHolder>() {

    private var eventList: MutableList<EventData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            view = View.inflate(parent.context, R.layout.item_upcoming_event_card, null),
            onEventClick = onEventClick,
            onFavoriteClick = onFavoriteClick
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    override fun getItemCount(): Int = eventList.size

    fun setList(eventList: List<EventData>) {
        this.eventList.clear()
        this.eventList.addAll(eventList)
        notifyDataSetChanged()
    }
}