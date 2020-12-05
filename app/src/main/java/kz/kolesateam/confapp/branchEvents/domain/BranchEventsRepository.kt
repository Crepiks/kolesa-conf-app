package kz.kolesateam.confapp.branchEvents.domain

import kz.kolesateam.confapp.branchEvents.data.models.EventApiData
import kz.kolesateam.confapp.common.models.ResponseData

interface BranchEventsRepository {
    fun getBranchEvents(branchId: Int): ResponseData<List<EventApiData>, String>
}