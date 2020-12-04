package kz.kolesateam.confapp.events.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.common.BaseViewHolder
import kz.kolesateam.confapp.events.presentation.models.HEADER_TYPE
import kz.kolesateam.confapp.events.presentation.models.UpcomingEventListItem
import kotlin.reflect.KFunction2

class UpcomingEventListAdapter(
    private val onBranchClick: (branchId: Int, branchTitle: String) -> Unit,
    private val onEventClick: (eventTitle: String) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<UpcomingEventListItem>>() {

    private val itemList: MutableList<UpcomingEventListItem> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<UpcomingEventListItem> {
        return when (viewType) {
            HEADER_TYPE -> createHeaderViewHolder(parent)
            else -> createBranchViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<UpcomingEventListItem>, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return itemList[position].type
    }

    fun setList(itemList: List<UpcomingEventListItem>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    private fun createHeaderViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = HeaderViewHolder(
        view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_upcoming_events_header,
            parent,
            false
        )
    )

    private fun createBranchViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = BranchViewHolder(
        view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_branch_card,
            parent,
            false
        ),
        parent = parent,
        onBranchClick = onBranchClick,
        onEventClick = onEventClick
    )
}