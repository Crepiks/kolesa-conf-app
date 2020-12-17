package kz.kolesateam.confapp.branchEvents.data.mappers

import kz.kolesateam.confapp.common.mappers.Mapper
import kz.kolesateam.confapp.branchEvents.data.models.SpeakerApiData
import kz.kolesateam.confapp.branchEvents.domain.models.SpeakerData

private const val DEFAULT_ID = 0
private const val DEFAULT_FULL_NAME = ""
private const val DEFAULT_JOB = ""
private const val DEFAULT_PHOTO_URL = ""

class SpeakerApiDataMapper : Mapper<SpeakerApiData, SpeakerData> {

    override fun map(data: SpeakerApiData): SpeakerData {
        return SpeakerData(
            id = data.id ?: DEFAULT_ID,
            fullName = data.fullName ?: DEFAULT_FULL_NAME,
            job = data.job ?: DEFAULT_JOB,
            photoUrl = data.photoUrl ?: DEFAULT_PHOTO_URL
        )
    }
}