package kz.kolesateam.confapp.upcomingEvents.data.mappers

import kz.kolesateam.confapp.common.mappers.Mapper
import kz.kolesateam.confapp.upcomingEvents.data.models.EventApiData
import kz.kolesateam.confapp.upcomingEvents.domain.models.EventData
import kz.kolesateam.confapp.upcomingEvents.domain.models.SpeakerData

private const val DEFAULT_ID = 0
private const val DEFAULT_START_TIME = ""
private const val DEFAULT_END_TIME = ""
private const val DEFAULT_TITLE = ""
private const val DEFAULT_DESCRIPTION = ""
private const val DEFAULT_PLACE = ""

private const val DEFAULT_SPEAKER_ID = 0
private const val DEFAULT_SPEAKER_FULL_NAME = ""
private const val DEFAULT_SPEAKER_JOB = ""
private const val DEFAULT_SPEAKER_PHOTO_URL = "0"

class EventApiDataMapper : Mapper<EventApiData, EventData> {

    override fun map(data: EventApiData): EventData {
        var speaker: SpeakerData = if (data.speaker == null) {
            getDefaultSpeaker()
        } else {
            SpeakerApiDataMapper().map(data.speaker)
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
            speaker = speaker
        )
    }

    private fun getDefaultSpeaker(): SpeakerData {
        return SpeakerData(
            id = DEFAULT_SPEAKER_ID,
            fullName = DEFAULT_SPEAKER_FULL_NAME,
            job = DEFAULT_SPEAKER_JOB,
            photoUrl = DEFAULT_SPEAKER_PHOTO_URL
        )
    }
}