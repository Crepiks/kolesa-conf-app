package kz.kolesateam.confapp.upcomingEvents.data.mappers

import kz.kolesateam.confapp.common.mappers.Mapper
import kz.kolesateam.confapp.common.mappers.NullableInputListMapperImpl
import kz.kolesateam.confapp.upcomingEvents.data.models.BranchApiData
import kz.kolesateam.confapp.upcomingEvents.domain.models.BranchData

private const val DEFAULT_ID = 0
private const val DEFAULT_TITLE = ""

class BranchApiDataMapper : Mapper<BranchApiData, BranchData> {

    override fun map(data: BranchApiData): BranchData {
        val eventListMapper =
            NullableInputListMapperImpl(EventApiDataMapper())
        val mappedEventList = eventListMapper.map(data.events)

        return BranchData(
            id = data.id ?: DEFAULT_ID,
            title = data.title ?: DEFAULT_TITLE,
            events = mappedEventList
        )
    }
}