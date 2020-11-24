package kz.kolesateam.confapp.events.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData

class BranchListAdapter(
        private val onBranchClick: (branchTitle: String) -> Unit,
        private val onEventClick: (eventTitle: String) -> Unit
) : RecyclerView.Adapter<BranchViewHolder>() {

    private val branchList: MutableList<BranchApiData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        return BranchViewHolder(
                view = View.inflate(parent.context, R.layout.item_branch_card, null),
                parent = parent,
                onBranchClick = onBranchClick,
                onEventClick = onEventClick
        )
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.bind(branchList[position])
    }

    override fun getItemCount(): Int = branchList.size

    fun setList(branchList: List<BranchApiData>) {
        this.branchList.clear()
        this.branchList.addAll(branchList)
        notifyDataSetChanged()
    }
}