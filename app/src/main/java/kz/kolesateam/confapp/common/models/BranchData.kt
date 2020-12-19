package kz.kolesateam.confapp.common.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class BranchData(
    val id: Int,
    val title: String,
    val events: List<EventData>
)
