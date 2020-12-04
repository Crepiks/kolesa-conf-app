package kz.kolesateam.confapp.branchEvents.presentation.models

import kz.kolesateam.confapp.branchEvents.data.models.EventApiData

const val HEADER_TYPE: Int = 0
const val EVENT_TYPE: Int = 1

sealed class BranchEventListItem(
    val type: Int
)

data class HeaderItem(
    val title: String
) : BranchEventListItem(type = HEADER_TYPE)

data class EventItem(
    val data: EventApiData
) : BranchEventListItem(type = EVENT_TYPE)