package kz.kolesateam.confapp.events.domain.models

data class EventData(
    val id: Int,
    val schedule: Schedule,
    val title: String,
    val description: String,
    val place: String,
    val speaker: SpeakerData
) {
    data class Schedule(
        val startTime: String,
        val endTime: String
    )
}