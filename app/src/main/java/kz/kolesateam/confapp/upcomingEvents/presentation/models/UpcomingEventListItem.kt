package kz.kolesateam.confapp.upcomingEvents.presentation.models

import kz.kolesateam.confapp.common.models.BranchData

const val HEADER_TYPE: Int = 0
const val BRANCH_TYPE: Int = 1

sealed class UpcomingEventListItem(
        val type: Int
)

data class HeaderItem(
        val userName: String
) : UpcomingEventListItem(type = HEADER_TYPE)

data class BranchItem(
        val data: BranchData
) : UpcomingEventListItem(type = BRANCH_TYPE)