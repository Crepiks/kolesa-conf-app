package kz.kolesateam.confapp.upcomingEvents.mappers

import kz.kolesateam.confapp.common.DataMapper
import kz.kolesateam.confapp.upcomingEvents.data.models.BranchApiData
import kz.kolesateam.confapp.upcomingEvents.domain.models.BranchData
import kz.kolesateam.confapp.upcomingEvents.domain.models.EventData

private const val DEFAULT_ID = 0
private const val DEFAULT_TITLE = ""
private val DEFAULT_EVENT_LIST = listOf<EventData>()

class BranchDataMapper : DataMapper<BranchApiData, BranchData> {

    override fun map(data: BranchApiData?): BranchData {
        if (data == null) {
            return getDefaultBranch()
        }
        val mappedEventList = data.events?.let { eventApiDataList ->
            eventApiDataList.map { eventApiData ->
                EventDataMapper().map(eventApiData)
            }
        }
        return BranchData(
            id = data.id ?: DEFAULT_ID,
            title = data.title ?: DEFAULT_TITLE,
            events = mappedEventList ?: DEFAULT_EVENT_LIST
        )
    }

    private fun getDefaultBranch(): BranchData = BranchData(
        id = DEFAULT_ID,
        title = DEFAULT_TITLE,
        events = DEFAULT_EVENT_LIST
    )
}