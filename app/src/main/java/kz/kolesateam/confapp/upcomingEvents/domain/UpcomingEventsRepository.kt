package kz.kolesateam.confapp.upcomingEvents.domain

import kz.kolesateam.confapp.common.models.BranchData
import kz.kolesateam.confapp.common.models.ResponseData

interface UpcomingEventsRepository {

    fun getUpcomingEvents(): List<BranchData>

    fun loadUpcomingEvents(): ResponseData<List<BranchData>, String>
}