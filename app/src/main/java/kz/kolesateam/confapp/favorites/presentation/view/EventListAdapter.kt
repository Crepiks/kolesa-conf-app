package kz.kolesateam.confapp.favorites.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.models.EventData

class EventListAdapter(
    private val onFavoriteClick: (event: EventData) -> Unit
) : RecyclerView.Adapter<EventViewHolder>() {

    private val eventList: MutableList<EventData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            view = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_favorites_event_card, parent, false,
                ),
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