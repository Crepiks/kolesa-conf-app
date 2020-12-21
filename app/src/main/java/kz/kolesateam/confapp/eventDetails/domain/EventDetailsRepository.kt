package kz.kolesateam.confapp.eventDetails.domain

import kz.kolesateam.confapp.common.models.EventData
import kz.kolesateam.confapp.common.models.ResponseData

interface EventDetailsRepository {

    fun loadEvent(eventId: Int): ResponseData<EventData, String>

    fun getEvent(): EventData
}