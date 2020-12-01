package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.presentation.models.BranchItem
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem

class BranchViewHolder(
        view: View,
        private val parent: ViewGroup,
        private val onBranchClick: (branchTitle: String) -> Unit,
        onEventClick: (eventTitle: String) -> Unit
) : BaseViewHolder<UpcomingEventListItem>(view) {

    private val header: View = view.findViewById(R.id.item_branch_card_header)
    private val branchTitle: TextView = view.findViewById(R.id.item_branch_card_branch_name)
    private val eventList: RecyclerView = view.findViewById(R.id.item_branch_card_event_list)
    private val eventListAdapter = EventListAdapter(onEventClick)

    init {
        setListeners()
    }

    override fun bind(data: UpcomingEventListItem) {
        val branchApiData: BranchApiData = (data as? BranchItem)?.data ?: return
        branchTitle.text = branchApiData.title
        eventList.layoutManager = LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
        eventList.adapter = eventListAdapter
        branchApiData.events?.let { eventListAdapter.setList(it) }
    }

    private fun setListeners() {
        header.setOnClickListener {
            val branchTitle = this.branchTitle.text.toString()
            onBranchClick(branchTitle)
        }
    }
}