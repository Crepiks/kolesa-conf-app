package kz.kolesateam.confapp.upcomingEvents.domain

import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.upcomingEvents.data.models.BranchApiData

interface UpcomingEventsRepository {

    fun getUpcomingEvents(): ResponseData<List<BranchApiData>, String>
}