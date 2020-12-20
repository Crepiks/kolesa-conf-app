package kz.kolesateam.confapp.upcomingEvents.presentation.view

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.BaseViewHolder
import kz.kolesateam.confapp.common.models.BranchData
import kz.kolesateam.confapp.common.models.EventData
import kz.kolesateam.confapp.upcomingEvents.presentation.models.BranchItem
import kz.kolesateam.confapp.upcomingEvents.presentation.models.UpcomingEventListItem

class BranchViewHolder(
    view: View,
    private val parent: ViewGroup,
    private val onBranchClick: (branchId: Int, branchTitle: String) -> Unit,
    onEventClick: (eventId: Int) -> Unit,
    onFavoriteClick: (event: EventData, isFavorite: Boolean) -> Unit
) : BaseViewHolder<UpcomingEventListItem>(view) {

    private val header: View = view.findViewById(R.id.item_branch_card_header)
    private val branchTitle: TextView = view.findViewById(R.id.item_branch_card_branch_name)
    private val eventList: RecyclerView = view.findViewById(R.id.item_branch_card_event_list)
    private val eventListAdapter = EventListAdapter(
        onEventClick = onEventClick,
        onFavoriteClick = onFavoriteClick
    )

    override fun bind(data: UpcomingEventListItem) {
        val branchData: BranchData = (data as? BranchItem)?.data ?: return
        branchTitle.text = branchData.title
        eventList.layoutManager =
            LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
        eventList.adapter = eventListAdapter
        branchData.events?.let { eventListAdapter.setList(it) }
        setListeners(branchData.id ?: return)
    }

    private fun setListeners(branchId: Int) {
        header.setOnClickListener {
            val branchTitle = this.branchTitle.text.toString()
            onBranchClick(branchId, branchTitle)
        }
    }
}