package kz.kolesateam.confapp.branchEvents.presentation.view

import android.view.View
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.branchEvents.presentation.models.BranchEventListItem
import kz.kolesateam.confapp.branchEvents.presentation.models.HeaderItem
import kz.kolesateam.confapp.common.BaseViewHolder

class HeaderViewHolder(val view: View) : BaseViewHolder<BranchEventListItem>(view) {

    private val title: TextView = view.findViewById(R.id.item_branch_events_header_title)

    override fun bind(data: BranchEventListItem) {
        val titleText: String = (data as? HeaderItem)?.title ?: return
        title.text = titleText
    }
}