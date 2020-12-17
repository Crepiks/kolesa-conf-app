package kz.kolesateam.confapp.upcomingEvents.domain

import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.upcomingEvents.domain.models.BranchData

interface UpcomingEventsRepository {

    fun getUpcomingEvents(): ResponseData<List<BranchData>, String>
}