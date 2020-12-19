package kz.kolesateam.confapp.events.domain.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SpeakerData(
    val id: Int,
    val fullName: String,
    val job: String,
    val photoUrl: String
)
