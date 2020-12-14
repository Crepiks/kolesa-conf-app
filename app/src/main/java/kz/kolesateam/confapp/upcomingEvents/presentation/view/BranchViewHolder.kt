package kz.kolesateam.confapp.upcomingEvents.presentation.view

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.BaseViewHolder
import kz.kolesateam.confapp.upcomingEvents.data.models.BranchApiData
import kz.kolesateam.confapp.upcomingEvents.presentation.models.BranchItem
import kz.kolesateam.confapp.upcomingEvents.presentation.models.UpcomingEventListItem

class BranchViewHolder(
    view: View,
    private val parent: ViewGroup,
    private val onBranchClick: (branchId: Int, branchTitle: String) -> Unit,
    onEventClick: (eventTitle: String) -> Unit
) : BaseViewHolder<UpcomingEventListItem>(view) {

    private val header: View = view.findViewById(R.id.item_branch_card_header)
    private val branchTitle: TextView = view.findViewById(R.id.item_branch_card_branch_name)
    private val eventList: RecyclerView = view.findViewById(R.id.item_branch_card_event_list)
    private val eventListAdapter = EventListAdapter(onEventClick)

    override fun bind(data: UpcomingEventListItem) {
        val branchApiData: BranchApiData = (data as? BranchItem)?.data ?: return
        branchTitle.text = branchApiData.title
        eventList.layoutManager =
            LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
        eventList.adapter = eventListAdapter
        branchApiData.events?.let { eventListAdapter.setList(it) }
        setListeners(branchApiData.id ?: return)
    }

    private fun setListeners(branchId: Int) {
        header.setOnClickListener {
            val branchTitle = this.branchTitle.text.toString()
            onBranchClick(branchId, branchTitle)
        }
    }
}