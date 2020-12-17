package kz.kolesateam.confapp.branchEvents.presentation.models

import kz.kolesateam.confapp.branchEvents.domain.models.EventData

const val HEADER_TYPE: Int = 0
const val EVENT_TYPE: Int = 1

sealed class BranchEventListItem(
    val type: Int
)

data class HeaderItem(
    val title: String
) : BranchEventListItem(type = HEADER_TYPE)

data class EventItem(
    val data: EventData
) : BranchEventListItem(type = EVENT_TYPE)