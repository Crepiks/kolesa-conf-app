package kz.kolesateam.confapp.events.domain.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class BranchData(
    val id: Int,
    val title: String,
    val events: List<EventData>
)
