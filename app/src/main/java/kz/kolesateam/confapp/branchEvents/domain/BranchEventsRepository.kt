package kz.kolesateam.confapp.branchEvents.domain

import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.events.domain.models.EventData

interface BranchEventsRepository {

    fun getBranchEvents(): List<EventData>

    fun loadBranchEvents(branchId: Int): ResponseData<List<EventData>, String>
}