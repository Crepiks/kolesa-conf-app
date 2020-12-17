package kz.kolesateam.confapp.upcomingEvents.data

import kz.kolesateam.confapp.common.mappers.ListMapperImpl
import kz.kolesateam.confapp.common.models.ResponseData
import kz.kolesateam.confapp.upcomingEvents.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.upcomingEvents.domain.models.BranchData
import kz.kolesateam.confapp.upcomingEvents.mappers.BranchDataMapper

class UpcomingEventsRepositoryImp(
    private val upcomingEventsDataSource: UpcomingEventsDataSource
) : UpcomingEventsRepository {

    override fun getUpcomingEvents(): ResponseData<List<BranchData>, String> = try {
        val response = upcomingEventsDataSource.getUpcomingEvents().execute()
        if (response.isSuccessful) {
            val body = response.body()!!
            val branchDataList = ListMapperImpl(BranchDataMapper()).map(body)
            ResponseData.Success(branchDataList)
        } else {
            ResponseData.Error(response.errorBody().toString())
        }
    } catch (e: Exception) {
        ResponseData.Error(e.localizedMessage)
    }
}