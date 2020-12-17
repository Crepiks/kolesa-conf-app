package kz.kolesateam.confapp.branchEvents.domain

import kz.kolesateam.confapp.branchEvents.domain.models.EventData
import kz.kolesateam.confapp.common.models.ResponseData

interface BranchEventsRepository {

    fun getBranchEvents(branchId: Int): ResponseData<List<EventData>, String>
}