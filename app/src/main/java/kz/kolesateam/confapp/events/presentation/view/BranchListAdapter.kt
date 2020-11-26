package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem

class BranchListAdapter(
        private val onBranchClick: (branchTitle: String) -> Unit,
        private val onEventClick: (eventTitle: String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList: MutableList<UpcomingEventsListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            1 -> HeaderViewHolder(view = View.inflate(parent.context, R.layout.item_upcoming_events_header, null))
            else -> BranchViewHolder(
                    view = View.inflate(parent.context, R.layout.item_branch_card, null),
                    parent = parent,
                    onBranchClick = onBranchClick,
                    onEventClick = onEventClick
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bind(itemList[position].data as String)
        } else if (holder is BranchViewHolder) {
            holder.bind(itemList[position].data as BranchApiData)
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return itemList[position].type
    }

    fun setList(itemList: List<UpcomingEventsListItem>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }
}