package kz.kolesateam.confapp.upcomingEvents.domain

import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.common.models.BranchData

interface UpcomingEventsRepository {

    fun getUpcomingEvents(): List<BranchData>

    fun loadUpcomingEvents(): ResponseData<List<BranchData>, String>
}