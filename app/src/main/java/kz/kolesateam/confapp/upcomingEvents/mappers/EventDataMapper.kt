package kz.kolesateam.confapp.upcomingEvents.mappers

import kz.kolesateam.confapp.common.DataMapper
import kz.kolesateam.confapp.upcomingEvents.data.models.EventApiData
import kz.kolesateam.confapp.upcomingEvents.domain.models.EventData

private const val DEFAULT_ID = 0
private const val DEFAULT_START_TIME = ""
private const val DEFAULT_END_TIME = ""
private const val DEFAULT_TITLE = ""
private const val DEFAULT_DESCRIPTION = ""
private const val DEFAULT_PLACE = ""
private val DEFAULT_SPEAKER = null

class EventDataMapper : DataMapper<EventApiData, EventData> {

    override fun map(data: EventApiData?): EventData {
        if (data == null) {
            return getDefaultEvent()
        }
        return EventData(
            id = data.id ?: DEFAULT_ID,
            schedule = EventData.Schedule(
                startTime = data.startTime ?: DEFAULT_START_TIME,
                endTime = data.endTime ?: DEFAULT_END_TIME
            ),
            title = data.title ?: DEFAULT_TITLE,
            description = data.description ?: DEFAULT_DESCRIPTION,
            place = data.place ?: DEFAULT_PLACE,
            speaker = SpeakerDataMapper().map(data.speaker)
        )
    }

    private fun getDefaultEvent(): EventData = EventData(
        id = DEFAULT_ID,
        schedule = EventData.Schedule(
            startTime = DEFAULT_START_TIME,
            endTime = DEFAULT_END_TIME
        ),
        title = DEFAULT_TITLE,
        description = DEFAULT_DESCRIPTION,
        place = DEFAULT_PLACE,
        speaker = SpeakerDataMapper().map(DEFAULT_SPEAKER)
    )
}