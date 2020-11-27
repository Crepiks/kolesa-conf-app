package kz.kolesateam.confapp.events.data.models

const val HEADER_TYPE: Int = 0
const val BRANCH_TYPE: Int = 1

sealed class UpcomingEventListItem(
        val type: Int
)

data class HeaderItem(
        val userName: String
) : UpcomingEventListItem(type = HEADER_TYPE)

data class BranchItem(
        val data: BranchApiData
) : UpcomingEventListItem(type = BRANCH_TYPE)