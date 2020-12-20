package kz.kolesateam.confapp.branchEvents.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.branchEvents.presentation.models.BranchEventListItem
import kz.kolesateam.confapp.branchEvents.presentation.models.HEADER_TYPE
import kz.kolesateam.confapp.common.BaseViewHolder
import kz.kolesateam.confapp.common.models.EventData

class EventListAdapter(
    private val onFavoriteClick: (event: EventData, isFavorite: Boolean) -> Unit,
    private val onEventClick: (eventId: Int) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<BranchEventListItem>>() {

    private val eventList: MutableList<BranchEventListItem> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BranchEventListItem> {
        return when (viewType) {
            HEADER_TYPE -> getHeaderViewHolder(parent)
            else -> getEventViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BranchEventListItem>, position: Int) {
        holder.bind(eventList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return eventList[position].type
    }

    override fun getItemCount(): Int = eventList.size

    fun setList(eventList: List<BranchEventListItem>) {
        this.eventList.clear()
        this.eventList.addAll(eventList)
        notifyDataSetChanged()
    }

    private fun getHeaderViewHolder(parent: ViewGroup): HeaderViewHolder {
        return HeaderViewHolder(
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_branch_events_header, parent, false)
        )
    }

    private fun getEventViewHolder(parent: ViewGroup): EventViewHolder {
        return EventViewHolder(
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_branch_event_card, parent, false),
            onFavoriteClick = onFavoriteClick,
            onEventClick = onEventClick
        )
    }
}