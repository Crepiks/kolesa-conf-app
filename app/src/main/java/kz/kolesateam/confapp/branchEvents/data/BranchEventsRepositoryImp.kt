package kz.kolesateam.confapp.branchEvents.data

import kz.kolesateam.confapp.branchEvents.data.mappers.EventApiDataMapper
import kz.kolesateam.confapp.branchEvents.domain.BranchEventsRepository
import kz.kolesateam.confapp.branchEvents.domain.models.EventData
import kz.kolesateam.confapp.common.mappers.ListMapperImpl
import kz.kolesateam.confapp.common.models.ResponseData

class BranchEventsRepositoryImp(
    private val branchEventsDataSource: BranchEventsDataSource
) : BranchEventsRepository {

    override fun getBranchEvents(branchId: Int): ResponseData<List<EventData>, String> = try {
        val response = branchEventsDataSource.getBranchEvents(branchId = branchId).execute()
        if (response.isSuccessful) {
            val body = response.body()!!
            val eventDataList =
                ListMapperImpl(EventApiDataMapper()).map(body)
            ResponseData.Success(eventDataList)
        } else {
            ResponseData.Error(response.errorBody().toString())
        }
    } catch (e: Exception) {
        ResponseData.Error(e.localizedMessage)
    }
}