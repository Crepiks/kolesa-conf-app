package kz.kolesateam.confapp.upcomingEvents.data

import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.upcomingEvents.data.models.BranchApiData
import kz.kolesateam.confapp.upcomingEvents.domain.UpcomingEventsRepository

class UpcomingEventsRepositoryImp(
    private val upcomingEventsDataSource: UpcomingEventsDataSource
) : UpcomingEventsRepository {
    
    override fun getUpcomingEvents(): ResponseData<List<BranchApiData>, String> = try {
        val response = upcomingEventsDataSource.getUpcomingEvents().execute()
        if (response.isSuccessful) {
            val body = response.body()!!
            ResponseData.Success(body)
        } else {
            ResponseData.Error(response.errorBody().toString())
        }
    } catch (e: Exception) {
        ResponseData.Error(e.localizedMessage)
    }
}