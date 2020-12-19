package kz.kolesateam.confapp.upcomingEvents.data.mappers

import kz.kolesateam.confapp.common.mappers.Mapper
import kz.kolesateam.confapp.events.domain.models.EventData
import kz.kolesateam.confapp.events.domain.models.SpeakerData
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository
import kz.kolesateam.confapp.upcomingEvents.data.models.EventApiData

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

class EventApiDataMapper(
    private val favoritesRepository: FavoritesRepository
) : Mapper<EventApiData, EventData> {

    override fun map(data: EventApiData): EventData {
        var speaker: SpeakerData = if (data.speaker == null) {
            getDefaultSpeaker()
        } else {
            SpeakerApiDataMapper().map(data.speaker)
        }

        val eventId = data.id ?: DEFAULT_ID

        return EventData(
            id = eventId,
            schedule = EventData.Schedule(
                startTime = data.startTime ?: DEFAULT_START_TIME,
                endTime = data.endTime ?: DEFAULT_END_TIME
            ),
            title = data.title ?: DEFAULT_TITLE,
            description = data.description ?: DEFAULT_DESCRIPTION,
            place = data.place ?: DEFAULT_PLACE,
            speaker = speaker,
            isFavorite = isFavorite(eventId)
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

    private fun isFavorite(eventId: Int): Boolean = favoritesRepository.isFavorite(eventId)
}