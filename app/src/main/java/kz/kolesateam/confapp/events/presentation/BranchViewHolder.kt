package kz.kolesateam.confapp.events.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData

class BranchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val branchTitle: TextView = view.findViewById(R.id.item_branch_card_branch_name)

    fun bind(branchApiData: BranchApiData) {
        branchTitle.text = branchApiData.title
    }

}