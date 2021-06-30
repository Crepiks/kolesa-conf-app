package kz.kolesateam.confapp.common.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventData(
    val id: Int,
    val schedule: Schedule,
    val title: String,
    val description: String,
    val place: String,
    val speaker: SpeakerData,
    val isFavorite: Boolean = true
) {
    data class Schedule(
        val startTime: String,
        val endTime: String
    )
}