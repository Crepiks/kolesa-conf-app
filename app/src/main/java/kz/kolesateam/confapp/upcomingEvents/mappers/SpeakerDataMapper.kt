package kz.kolesateam.confapp.upcomingEvents.mappers

import kz.kolesateam.confapp.common.DataMapper
import kz.kolesateam.confapp.upcomingEvents.data.models.SpeakerApiData
import kz.kolesateam.confapp.upcomingEvents.domain.models.SpeakerData

private const val DEFAULT_ID = 0
private const val DEFAULT_FULL_NAME = ""
private const val DEFAULT_JOB = ""
private const val DEFAULT_PHOTO_URL = ""

class SpeakerDataMapper : DataMapper<SpeakerApiData, SpeakerData> {

    override fun map(data: SpeakerApiData?): SpeakerData {
        if (data == null) {
            return getDefaultSpeaker()
        }
        return SpeakerData(
            id = data.id ?: DEFAULT_ID,
            fullName = data.fullName ?: DEFAULT_FULL_NAME,
            job = data.job ?: DEFAULT_JOB,
            photoUrl = data.photoUrl ?: DEFAULT_PHOTO_URL
        )
    }

    private fun getDefaultSpeaker(): SpeakerData = SpeakerData(
        id = DEFAULT_ID,
        fullName = DEFAULT_FULL_NAME,
        job = DEFAULT_JOB,
        photoUrl = DEFAULT_PHOTO_URL
    )
}