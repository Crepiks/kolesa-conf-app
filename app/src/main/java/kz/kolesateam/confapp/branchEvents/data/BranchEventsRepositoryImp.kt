package kz.kolesateam.confapp.branchEvents.data

import kz.kolesateam.confapp.branchEvents.data.models.EventApiData
import kz.kolesateam.confapp.branchEvents.domain.BranchEventsRepository
import kz.kolesateam.confapp.common.models.ResponseData

class BranchEventsRepositoryImp(
    private val branchEventsDataSource: BranchEventsDataSource
) : BranchEventsRepository {

    override fun getBranchEvents(branchId: Int): ResponseData<List<EventApiData>, String> = try {
        val response = branchEventsDataSource.getBranchEvents(branchId = branchId).execute()
        if (response.isSuccessful) {
            ResponseData.Success(response.body()!!)
        } else {
            ResponseData.Error(response.errorBody().toString())
        }
    } catch (e: Exception) {
        ResponseData.Error(e.localizedMessage)
    }
}