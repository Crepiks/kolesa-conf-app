package kz.kolesateam.confapp.events.presentation

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData

class BranchViewHolder(view: View, private val parent: ViewGroup) : RecyclerView.ViewHolder(view) {

    private val branchTitle: TextView = view.findViewById(R.id.item_branch_card_branch_name)
    private val eventList: RecyclerView = view.findViewById(R.id.item_branch_card_event_list)
    private val eventListAdapter = EventListAdapter()

    fun bind(branchApiData: BranchApiData) {
        branchTitle.text = branchApiData.title
        eventList.layoutManager = LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
        eventList.adapter = eventListAdapter
        branchApiData.events?.let { eventListAdapter.setList(it) }
    }

}